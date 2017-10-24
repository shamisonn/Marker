# Marker

コンプロとかの採点用のWebサイト用のリポジトリです。

# usage

```
$ mvn clean package
```
jarが生成されるので実行
```
$ java -jar target/Marker-1.0-SNAPSHOT.jar
```

`localhost:4567`を覗きに行くと内容が見れる。

# tips

- Spark Java: http://sparkjava.com/documentation
- Template Engine Velocity: http://velocity.apache.org/engine/1.7/user-guide.html

# todo

- JUnitによるテストの追加
- DBまわりの実装
  - `com.zzlab.repositories`にインターフェイスを追加し、
  それに合わせて`com.zzlab.repoImpl`に実装をぶち込んでいく
  - DBには`sqlite3`を使う予定。jdbcをmavenにあとで入れる。
- UI仕様の策定