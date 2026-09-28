import { useEffect, useState } from 'react';
import { getSummary } from '../api/tasks';
import type { Summary } from '../types/task';

export default function Dashboard() {
  const [summary, setSummary] = useState<Summary | null>(null);

  useEffect(() => {
    getSummary().then(setSummary);
  }, []);

  return (
    <section className="container">
      <h1>ダッシュボード</h1>
      <div className="cards">
        <div className="card">
          <span>総タスク数</span>
          <b>{summary?.total ?? '-'}</b>
        </div>

        <div className="card">
          <span>TODO</span>
          <b>{summary?.todo ?? '-'}</b>
        </div>

        <div className="card">
          <span>DOING</span>
          <b>{summary?.doing ?? '-'}</b>
        </div>

        <div className="card">
          <span>DONE</span>
          <b>{summary?.done ?? '-'}</b>
        </div>
      </div>
    </section>
  );
}