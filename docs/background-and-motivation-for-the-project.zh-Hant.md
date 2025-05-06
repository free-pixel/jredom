

## 項目產生的背景與動機

- [简体中文 🇨🇳](background-and-motivation-for-the-project.zh-CN.md)
- [English 🇺🇸](background-and-motivation-for-the-project.en.md)
- [繁體中文 🇹🇼](background-and-motivation-for-the-project.zh-Hant.md)
- [日本語 🇯🇵](background-and-motivation-for-the-project.ja.md)


多年前，我從事遊戲開發工作，Redis 開始在遊戲產業中流行起來。和互聯網應用一樣，Redis 在遊戲中同樣被用作業務緩存。

在使用 Redis 之前，業界普遍使用 Memcached 作為緩存系統。但 MC 只支援字串類型，缺乏 Redis 那樣豐富的資料結構。要緩存物件時，通常有兩種做法：一是將物件序列化成 JSON 字串儲存；另一是序列化為二進位資料後儲存。

我們當時採用 JSON 方式將 Java 物件序列化為字串，再反序列化還原。初期還能接受，但隨著專案擴大，代碼中充滿 `serObj()`、`descObj()` 等函數，顯得雜亂。雖有人建議用反射統一封裝，但終究不夠優雅。

後來我們使用 Jackson 統一序列化邏輯，但其可用性還是比不上 Hibernate、MyBatis 等 ORM 框架。因此，我一直有開發 Redis ORM 的想法。

Spring 推出了 RedisTemplate，一定程度滿足需求，我也暫時擱置了自研。

直到近年 AI 編程開始流行，我也用 Cursor 等工具完成了一些項目。最近終於有時間，把 Redis ORM 的構想交給 AI 實作出來，還自動補齊了測試用例，整體過程不到一小時。這種 vibe coding 的開發體驗，讓我重拾那份未完成的熱情，也讓我找回心流狀態。

vibe coding 有句話很打動我：**「專注於解決問題，而不是寫程式碼本身。」**

這個項目與其說是傳統框架，不如說是我對 Redis ORM 的開發實驗。我尚未規劃其最終方向，但藉由 AI 和程式碼，我完整分享了這些想法於開源社群。

我相信還有其他開發者有相似思路，期望藉此一同交流，探討更優雅的 Redis ORM 解法。
