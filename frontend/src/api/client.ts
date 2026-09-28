export async function api<T>(url:string,opt?:RequestInit):Promise<T>{const r=await fetch(url,{headers:{'Content-Type':'application/json',...(opt?.headers||{})},...opt});
if(!r.ok){throw await r.json().catch(()=>(
    {message:'通信に失敗しました。'}
))}
    return r.status===204?undefined as T:r.json()
}