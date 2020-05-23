export interface Ticket {
  id?: string;
  tracker: string;
  subject: string;
  description: string;
  status: string;
  category: string;
  priority: string;
  assignee: string;
  startDate?: string;
  dueDate?: string;
  estimatedTime?: string;
}
