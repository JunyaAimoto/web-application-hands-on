export type TaskStatus = 'TODO' | 'DOING' | 'DONE';
export interface Task{id:number;title:string;description:string|null;userId:number;userName:string;status:TaskStatus;dueDate:string|null;createdAt:string;updatedAt:string};
export interface User{id:number;name:string;email:string;department:string|null};
export interface TaskRequest{title:string;description:string;userId:number;status:TaskStatus;dueDate:string};
export interface Summary{total:number;todo:number;doing:number;done:number};