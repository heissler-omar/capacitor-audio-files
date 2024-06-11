import { WebPlugin } from '@capacitor/core';

import type { AudioFilesPlugin } from './definitions';

export class AudioFilesWeb extends WebPlugin implements AudioFilesPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
