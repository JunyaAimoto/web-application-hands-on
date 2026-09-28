import{Link,Outlet}from'react-router-dom';

export default function Layout(){
    return 
        <>
            <header>
                <div className="bar">
                    <Link to="/">タスク管理アプリ</Link>
                    <nav>
                        <Link to="/">ダッシュボード</Link>
                        <Link to="/tasks">タスク一覧</Link>
                        <Link to="/tasks/new">タスク登録</Link>
                    </nav>
                </div>
            </header>
            <main>
                <Outlet/>
            </main>
        </>
}