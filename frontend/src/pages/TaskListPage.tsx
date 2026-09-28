const tasks = [
  {
    id: 1,
    title: '資料を作成する',
    userName: '山田太郎',
    status: 'TODO',
    dueDate: '2026-10-05',
    updatedAt: '2026-10-01 10:00',
  },
  {
    id: 2,
    title: 'レビューを実施する',
    userName: '佐藤花子',
    status: 'DOING',
    dueDate: '2026-10-07',
    updatedAt: '2026-10-01 11:00',
  },
  {
    id: 3,
    title: 'テストを実施する',
    userName: '鈴木一郎',
    status: 'DONE',
    dueDate: '2026-10-03',
    updatedAt: '2026-10-01 12:00',
  },
];

export default function Tasks() {
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
    </section>
  );
}
