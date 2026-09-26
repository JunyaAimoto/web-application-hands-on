INSERT INTO users(name,email,department) VALUES
('山田 太郎','taro.yamada@example.com','営業部'),
('佐藤 花子','hanako.sato@example.com','開発部'),
('鈴木 一郎','ichiro.suzuki@example.com','管理部')
ON CONFLICT DO NOTHING;
INSERT INTO tasks(title,description,user_id,status,due_date)
SELECT '要件定義資料を確認する','要件定義書の内容を確認する',id,'TODO',CURRENT_DATE+7 FROM users WHERE email='taro.yamada@example.com'
AND NOT EXISTS (SELECT 1 FROM tasks WHERE title='要件定義資料を確認する');
INSERT INTO tasks(title,description,user_id,status,due_date)
SELECT '画面モックを作成する','タスク管理画面のモックを作成する',id,'DOING',CURRENT_DATE+10 FROM users WHERE email='hanako.sato@example.com'
AND NOT EXISTS (SELECT 1 FROM tasks WHERE title='画面モックを作成する');
INSERT INTO tasks(title,description,user_id,status,due_date)
SELECT 'テスト計画を作成する','正常系・異常系のテスト計画を作成する',id,'DONE',CURRENT_DATE+3 FROM users WHERE email='ichiro.suzuki@example.com'
AND NOT EXISTS (SELECT 1 FROM tasks WHERE title='テスト計画を作成する');
