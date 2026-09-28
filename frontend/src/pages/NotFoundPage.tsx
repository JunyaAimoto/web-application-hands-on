import { Link } from 'react-router-dom';

export default function NotFound() {
  return (
    <section className="container">
      <h1>404</h1>
      <p>ページが見つかりません。</p>
      <Link to="/">トップへ戻る</Link>
    </section>
  );
}
