# Async Socket Server & Client

このプロジェクトは、Java 21のVirtual Threads (Project Loom) を活用した非同期のソケット通信を実装し、適切なユニットテストを行うサーバー・クライアントアプリケーションです。

## 1. プロジェクト構成

```shell
async-socket/
│── server/
│   ├── AsyncSocketServer.java      # 非同期ソケットサーバー
│   ├── AsyncSocketServerTest.java  # サーバーのユニットテスト
│── client/
│   ├── AsyncSocketClient.java      # 非同期ソケットクライアント
│   ├── AsyncSocketClientTest.java  # クライアントのユニットテスト
│── lib/                            # JUnit 5 & Mockito ライブラリ
│── build.gradle                    # Gradle のビルド設定
```

---

## 2. ソケット通信の動作

### サーバーの動作

1. `ServerSocket`を開き、ポート`12345`で接続を待機。
2. クライアントが接続すると、`Virtual Threads`を使って非同期に処理。
3. クライアントからのメッセージを受信し、大文字に変換して返信。
4. クライアントが切断するまで継続。

### クライアントの動作

1. サーバーへ接続し、ユーザーの入力を送信。
2. サーバーからの応答を非同期スレッドで受信。
3. `exit`を入力するとクライアントが終了。

---

## 3. 非同期の動作

- `Executors.newVirtualThreadPerTaskExecutor()` を使用して、クライアントごとに仮想スレッドを割り当てる。
- 軽量なスレッドを使用するため、大量のクライアントを同時処理可能。
- クライアント側では`ExecutorService`を使い、サーバーからの応答を非同期で受信。

---

## 4. ユニットテスト

### サーバーのテスト (`AsyncSocketServerTest.java`)

✅ クライアントが接続できるか

✅ クライアントから送信したメッセージが正しく受信・処理されるか

✅ サーバーの応答が適切か

### クライアントのテスト (`AsyncSocketClientTest.java`)

✅ サーバーへ正しくメッセージを送信できるか

✅ サーバーからのレスポンスを正しく受信できるか

✅ 異常時（接続エラー時）のハンドリングが適切か

---

## 5. セットアップ

### 依存関係のインストール

```sh
gradle build
```

---

## 6. サーバー & クライアントの実行

### **サーバーの起動**

```sh
cd async-socket
javac server/AsyncSocketServer.java
java -cp . server.AsyncSocketServer
```

### **クライアントの起動**

```sh
cd async-socket
javac client/AsyncSocketClient.java
java -cp . client.AsyncSocketClient
```

`exit`を入力するとクライアントが終了。

---

## 7. ユニットテストの実行

```sh
gradle test
```

---

## 8. まとめ

- **非同期ソケット通信の仕組みを理解できる。**
- **Virtual Threadsによるスケーラブルな処理を体験できる。**
- **JUnit 5とMockitoを活用したユニットテストを実装し、信頼性を向上。**
