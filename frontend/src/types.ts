export interface Condition { operator: string; value?: number; boolValue?: boolean }
export interface Choice { id: string; text: string; nextNodeId: string; quickTimeEvent?: boolean; timeLimit?: number; requirements?: Record<string, Condition>; effects?: Record<string, number>; flashbackTrigger?: string }
export interface Interactable { id: string; name: string; description: string; hidden?: boolean; disturbing?: boolean; specialEffect?: string; visibilityConditions?: Record<string, Condition> }
export interface StoryNode { id: string; scene: string; type: string; publicNarrative: string; innerThoughts?: string; choices: Choice[]; interactables: Interactable[]; disturbing?: boolean }
export interface GameState { sessionId: string; currentNodeId: string; selfAwareness: number; narratorReliability: number; poirotSuspicion: number; sanity: number; collectedMemories: string[]; foundClues: string[]; interacted: Record<string, boolean>; characterRelations: Record<string, number>; elapsedMinutes: number }
export interface MemoryFragment { id: string; title: string; content: string; type: string; selfAwarenessReward: number }
export interface Flashback { id: string; type: string; title: string; content: string; audioPath?: string; duration: number; sanityCost: number; intensity: number }
export interface Ending { id: string; title: string; content: string; tone: string }
export interface GameResponse { type: 'NODE' | 'FLASHBACK' | 'ENDING' | 'ERROR'; message?: string; node?: StoryNode; state: GameState; triggeredMemories?: MemoryFragment[]; flashbacks?: Flashback[]; ending?: Ending }
export interface InteractionResponse { success: boolean; description: string; state: GameState; triggeredMemory?: MemoryFragment; flashbacks?: Flashback[]; interactable?: Interactable }
export interface ApiResponse<T> { success: boolean; message?: string; data: T }
