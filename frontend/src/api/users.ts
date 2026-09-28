import{api}from'./client';
import type{User}from'../types/task';

export const getUser=(id:number)=>api<User>('/api/users/'+id)