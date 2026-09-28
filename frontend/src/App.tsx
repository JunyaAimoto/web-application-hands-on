import { Route, Routes } from 'react-router-dom';
import Dashboard from './pages/DashboardPage';
import Tasks from './pages/TaskListPage';
import NotFound from './pages/NotFoundPage';

export default function App() {
  return (
    <Routes>
      <Route path="/" element={<Dashboard />} />
      <Route path="/tasks" element={<Tasks />} />
      <Route path="*" element={<NotFound />} />
    </Routes>
  );
}
