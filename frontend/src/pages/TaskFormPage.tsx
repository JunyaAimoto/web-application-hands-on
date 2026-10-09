import{useEffect,useState}from'react';
import{useNavigate,useParams}from'react-router-dom';
import{createTask,getTask,updateTask}from'../api/tasks';

export default function Form(){
    const{id}=useParams();
    const edit=!!id;
    const nav=useNavigate();
    const[t,setT]=useState<any>();
    const[title,setTitle]=useState('');
    const[desc,setDesc]=useState('');
    const[user,setUser]=useState('1');
    const[status,setStatus]=useState<any>('TODO');
    const[due,setDue]=useState('');
    const [error, setError] = useState('');

    useEffect(()=>{
        if(id)getTask(Number(id))
            .then(x => {
                setT(x);
                setTitle(x.title);
                setDesc(x.description||'');
                setUser(String(x.userId));
                setStatus(x.status);
                setDue(x.dueDate||'')
            })
            .catch((e: any) => {
                setError(
                    e?.message ?? 'タスクの取得に失敗しました。'
                );
            });
    },[id]);
    
    const submit = async (e: any) => {
        e.preventDefault();

        if (!title.trim()) {
            return alert('タイトルは必須です。');
        }

        setError('');

        try {
            const x = {
                title: title.trim(),
                description: desc,
                userId: Number(user),
                status,
                dueDate: due
            };

            const r = edit
                ? await updateTask(Number(id), x)
                : await createTask(x);

            nav('/tasks/' + r.id);

        } catch (e: any) {
            setError(
                e?.message ?? 'タスクの保存に失敗しました。'
            );
        }
    }; 
    return (
        <section>
            <h1>{edit?'タスク編集':'タスク登録'}</h1>
            {error && (
                <p className="error">
                    {error}
                </p>
            )} 
            <form onSubmit={submit} className="form">
                <label>
                    タイトル
                    <input value={title} onChange={e=>setTitle(e.target.value)}/>
                </label>
                <label>
                    説明
                    <textarea value={desc} onChange={e=>setDesc(e.target.value)}/>
                </label>
                <label>
                    担当者
                    <select value={user} onChange={e=>setUser(e.target.value)}>
                        <option value="1">山田 太郎</option>
                        <option value="2">佐藤 花子</option>
                        <option value="3">鈴木 一郎</option>
                    </select>
                </label>
                <label>
                    ステータス
                    <select value={status} onChange={e=>setStatus(e.target.value)}>
                        <option>TODO</option>
                        <option>DOING</option>
                        <option>DONE</option>
                    </select>
                </label>
                <label>
                    期限
                    <input type="date" value={due} onChange={e=>setDue(e.target.value)}/>
                </label>
                <button>保存</button>
            </form>
        </section>
    );
}