# Chromeからのアクセスに任意のRequestHeaderを追加する

Chrome拡張機能 [ModHeader](https://chrome.google.com/webstore/detail/modheader/idgpnmonknjnojddfkpgkljpfnnfcklj?hl=ja)
 を使って、リクエストに任意の RequestHeader を追加する方法を記載する。

## 設定方法

RequestHeaderにセットしたいキーとバリューを設定する。ここでは認証情報として"x-uid"に登録済ユーザのIDを設定し、Top画面にアクセスした。
Top画面は参照に認証が必要だが、画像の通り表示できている。

![認証情報を設定してTOP画面にアクセスした図](/docs/img/chrome_extension.png)

以上。