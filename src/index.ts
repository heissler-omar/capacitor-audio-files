import { registerPlugin } from '@capacitor/core';

import type { AudioFilesPlugin } from './definitions';

const AudioFiles = registerPlugin<AudioFilesPlugin>('AudioFiles', {
  web: () => import('./web').then(m => new m.AudioFilesWeb()),
});

export * from './definitions';
export { AudioFiles };
