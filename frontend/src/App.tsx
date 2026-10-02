import { Route, Routes } from 'react-router-dom';
import Dashboard from './pages/DashboardPage';
import NotFound from './pages/NotFoundPage';
import Layout from'./components/Layout'; 
import TaskList from './pages/TaskListPage'; 
import TaskDetail from './pages/TaskDetailPage'; 
import TaskCreate from './pages/TaskFormPage'; 
import TaskEdit from './pages/TaskFormPage';

export default function App() {
  return (
    <Routes>
      <Route element={<Layout />}> 
        <Route path="/" element={<Dashboard />} />  
        <Route path="/tasks" element={<TaskList />} /> 
        <Route path="/tasks/:id" element={<TaskDetail />} /> 
        <Route path="/tasks/new" element={<TaskCreate />} /> 
        <Route path="/tasks/:id/edit" element={<TaskEdit />} /> 
        <Route path="*" element={<NotFound />} />
      </Route>
    </Routes>
  );
}
