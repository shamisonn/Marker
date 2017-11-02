package com.zzlab.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LoginManager {

    private String secret;
    private Set<String> blackList;

    public LoginManager(String secret) {
        this.secret = secret;
        this.blackList = Collections.synchronizedSet(new HashSet<>());
    }

    /**
     * JSON Web Token を作成する。作成したTokenはクライアントに渡し、
     * 以後、verifyToken にてTokenの正しさをチェックしながら、
     * 正しいTokenを持つクライアントのみアクセスできるようにする。
     *
     * @param id ユーザidをパラメタに含める。
     * @return token string
     */
    public String createToken(long id) {
        String token = null;
        try {
            Algorithm alg = Algorithm.HMAC256(this.secret);
            ZonedDateTime from = ZonedDateTime.now();
            ZonedDateTime to = from.plusDays(1L);//期限は1日
            token = JWT.create()
                    .withClaim("user_id", id)
                    .withIssuer("marker.zzlab.server")
                    .withIssuedAt(Date.from(from.toInstant()))
                    .withExpiresAt(Date.from(to.toInstant()))
                    .sign(alg);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return token;
    }

    /**
     * Tokenの正しさをチェックする。
     * 正しくない場合は例外が吐かれて
     *
     * @param token
     * @return
     */
    public boolean verifyToken(String token) {
        if (this.blackList.contains(token)) {
            return false;
        }
        try {
            Algorithm alg = Algorithm.HMAC256(this.secret);
            JWTVerifier verifier = JWT.require(alg)
                    .withIssuer("marker.zzlab.server")
                    .build();

            DecodedJWT jwt = verifier.verify(token);
            if (jwt.getExpiresAt().before(new Date())) {
                return false;
            }
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * tokenの無効化。black listに入れることで無効化する。
     *
     * @param token
     */
    public synchronized void invalidateToken(String token) {
        try {
            JWT.decode(token);
        } catch (JWTDecodeException e) {
            e.printStackTrace();
            return;
        }
        if (this.blackList.contains(token)) {
            blackList.add(token);
        }
    }

    /**
     * black listから不必要なものを消す。
     */
    private synchronized void cleanBlackList() {
        for (String token : this.blackList) {
            DecodedJWT decode = JWT.decode(token);
            if (decode.getExpiresAt().before(new Date())) {
                blackList.remove(token);
            }
        }
    }

    /**
     * cleanを定期的に行うやつ。
     */
    public void scheduledCleanBlackList() {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(this::cleanBlackList, 0, 1, TimeUnit.DAYS);
    }
}
