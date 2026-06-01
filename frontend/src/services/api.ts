import type { ApiResponse, GameResponse, InteractionResponse, MemoryFragment } from '../types';

const jsonHeaders = { 'Content-Type': 'application/json' };
async function request<T>(url: string, options: RequestInit = {}): Promise<T> {
  const response = await fetch(url, { credentials: 'include', ...options });
  const payload = (await response.json()) as ApiResponse<T>;
  if (!payload.success) throw new Error(payload.message || '请求失败');
  return payload.data;
}
export const api = {
  start: () => request<GameResponse>('/api/game/start', { method: 'POST' }),
  state: () => request<GameResponse>('/api/game/state'),
  choice: (choiceId: string, timeTaken?: number) => request<GameResponse>('/api/game/choice', { method: 'POST', headers: jsonHeaders, body: JSON.stringify({ choiceId, timeTaken }) }),
  interact: (interactableId: string) => request<InteractionResponse>('/api/game/interact', { method: 'POST', headers: jsonHeaders, body: JSON.stringify({ interactableId, action: 'EXAMINE' }) }),
  quicktime: (eventId: string, choiceId: string, timeTaken: number, timeLimit: number) => request<GameResponse>('/api/game/quicktime', { method: 'POST', headers: jsonHeaders, body: JSON.stringify({ eventId, choiceId, timeTaken, timeLimit }) }),
  memories: () => request<MemoryFragment[]>('/api/game/memories'),
  restart: () => request<GameResponse>('/api/game/restart', { method: 'POST' })
};
