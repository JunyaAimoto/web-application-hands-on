# マスター版運用メモ

このリポジトリはレビュー者用の完成版です。
受講者へ渡す課題版は、この完成版をコピーしてから実装の一部を削除してください。

課題版では、React画面、API連携、Controller、Service、Repository、Entity、バリデーション、CRUD処理などを段階的に欠落させ、手順書に従って追加させます。

Gitへ登録する前に、README、環境依存ファイル、秘密情報、生成物を確認してください。


## 2026-09-26 修正
- TaskRepository.search の keyword=null 時に PostgreSQL が lower(bytea) と解釈する問題を修正。
- `:keyword is null` を廃止し、`coalesce(:keyword,'')` を文字列検索条件に使用。
