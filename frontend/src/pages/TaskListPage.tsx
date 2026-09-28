import { useEffect, useState } from 'react';
import type { Task } from '../types/task';
import { getTasks } from '../api/tasks';

export default function Tasks() {

  const [tasks, setTasks] = useState<Task[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const loadTasks = async () => {
      try {
        setLoading(true);
        setError('');

        const data = await getTasks();
        setTasks(data);
      } catch (e) {
        setError('タスク一覧の取得に失敗しました。');
      } finally {
        setLoading(false);
      }
    };
    loadTasks();
  }, []);

  return (
    <section className="container">
      <h1>タスク一覧</h1>
       <div>
        <a href="/tasks/new">タスクを登録する</a>
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
