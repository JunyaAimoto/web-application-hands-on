import { useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';

import type { Task } from '../types/task';
import { getTasks } from '../api/tasks';

export default function Tasks() {

  const [tasks, setTasks] = useState<Task[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [searchParams, setSearchParams] = useSearchParams();

  const keyword = searchParams.get('keyword') ?? '';
  const status = searchParams.get('status') ?? '';
  const userId = searchParams.get('userId') ?? '';
  const [keywordInput, setKeywordInput] = useState(keyword);
  const [statusInput, setStatusInput] = useState(status);
  const [userIdInput, setUserIdInput] = useState(userId);

  const handleSearch = () => {
    const params = new URLSearchParams();

    if (keywordInput.trim()) {
      params.set('keyword', keywordInput.trim());
    }

    if (statusInput) {
      params.set('status', statusInput);
    }

    if (userIdInput) {
      params.set('userId', userIdInput);
    }

    setSearchParams(params);
  };

  useEffect(() => {
    const loadTasks = async () => {
      try {
        setLoading(true);
        setError('');

        const data = await getTasks({
          keyword: keyword || undefined,
          status: status || undefined,
          userId: userId ? Number(userId) : undefined
        });

        setTasks(data);
      } catch (e: any) {
        setError(
            e?.message ?? 'タスク一覧の取得に失敗しました。'
        )
      } finally {
        setLoading(false);
      }
    };

    loadTasks();
  }, [keyword, status, userId]);

  return (
    <section className="container">
      <h1>タスク一覧</h1>
      <div>
        <div>
          <label>
            キーワード：
            <input
              type="text"
              value={keywordInput}
              onChange={(e) => setKeywordInput(e.target.value)}
            />
          </label>
        </div>

        <div>
          <label>
            ステータス：
            <select
              value={statusInput}
              onChange={(e) => setStatusInput(e.target.value)}
            >
              <option value="">すべて</option>
              <option value="TODO">TODO</option>
              <option value="DOING">DOING</option>
              <option value="DONE">DONE</option>
            </select>
          </label>
        </div>

        <div>
          <label>
            担当者ID：
            <input
              type="number"
              value={userIdInput}
              onChange={(e) => setUserIdInput(e.target.value)}
            />
          </label>
        </div>

        <button type="button" onClick={handleSearch}>
          検索
        </button>

        <div>
          <a href="/tasks/new">タスクを登録する</a>
        </div>
      </div>
      {
        tasks.length ?
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>タイトル</th>
              <th>担当者</th>
              <th>ステータス</th>
              <th>期限</th>
              <th>更新日時</th>
              <th>詳細</th>
            </tr>
          </thead>

          <tbody>
            {
              tasks.map((task) => (
                <tr key={task.id}>
                  <td>{task.id}</td>
                  <td>{task.title}</td>
                  <td>{task.userName}</td>
                  <td>{task.status}</td>
                  <td>{task.dueDate}</td>
                  <td>{task.updatedAt}</td>
                  <td>
                    <a href={`/tasks/${task.id}`}>詳細</a>
                  </td>
                </tr>
              ))
            }
          </tbody>
        </table>
        : <p className="empty">該当するタスクがありません。</p>
      }
      { loading && <p>読み込み中...</p> }
      { error && <p>{error}</p> }
    </section>
  );
}
