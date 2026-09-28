import{api}from'./client';

export interface Summary { total:number; todo:number; doing:number; done:number }
export const getSummary = () => api<Summary>('/api/tasks/summary');
