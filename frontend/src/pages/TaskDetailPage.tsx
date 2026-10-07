import{useEffect,useState}from'react';
import{Link,useNavigate,useParams}from'react-router-dom';
import{deleteTask,getTask}from'../api/tasks';

export default function Detail(){
    const{id}=useParams();
    const nav=useNavigate();
    const[t,setT]=useState<any>();
    
    useEffect(()=>{
        if(id)getTask(Number(id)).then(setT)
    },[id]);
    if(!t)
        return 
            <p>読み込み中...</p>;
        return (
            <section>
                <h1>タスク詳細</h1>
                <dl>
                    <dt>ID</dt>
                    <dd>{t.id}</dd>
                    <dt>タイトル</dt>
                    <dd>{t.title}</dd>
                    <dt>説明</dt>
                    <dd>{t.description ?? '-'}</dd>
                    <dt>担当者</dt>
                    <dd>{t.userName}</dd>
                    <dt>ステータス</dt>
                    <dd>{t.status}</dd>
                    <dt>期限</dt>
                    <dd>{t.dueDate ?? '-'}</dd>
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