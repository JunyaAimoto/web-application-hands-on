import{useEffect,useState}from'react';
import{Link,useNavigate,useParams}from'react-router-dom';
import{deleteTask,getTask}from'../api/tasks';

export default function Detail(){
    const{id}=useParams();
    const nav=useNavigate();
    const[t,setT]=useState<any>();
    const [error, setError] = useState('');
    
    useEffect(() => {
        if (!id) return;

        getTask(Number(id))
            .then(setT)
            .catch((e: any) => {
                setError(
                    e?.message ?? 'タスク詳細の取得に失敗しました。'
                );
            });
    }, [id]);

    if (error) {
        return (
            <section>
                <h1>エラー</h1>
                <p>{error}</p>
            </section>
        );
    }

    if(!t) {
        return 
            <p>読み込み中...</p>;
    }
    
    return (
        <section>
            <h1>タスク詳細</h1>
            <dl className="detail">
                {Object.entries({
                    ID:t.id,
                    タイトル:t.title,
                    説明:t.description||'-',
                    担当者:t.userName,
                    ステータス:t.status,
                    期限:t.dueDate||'-'
                }).map(([k,v])=><>
                    <dt>{k}</dt>
                    <dd>{String(v)}</dd>
                </>)}
            </dl>
            <Link className="btn" to={'/tasks/'+t.id+'/edit'}>編集</Link> 
            <button onClick={async()=>{
                if(confirm('このタスクを削除しますか？')){
                    await deleteTask(t.id);
                    nav('/tasks')
                }
            }}>削除</button>
        </section>
    );
}
