import { WebPlugin } from '@capacitor/core';

import type { AudioFilesPlugin } from './definitions';

export class AudioFilesWeb extends WebPlugin implements AudioFilesPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }

  async showMessage(options: { msg: string; }): Promise<{ value: string; }> {
    alert(options.msg)
    return {value: options.msg}
  }

  async requestDirectoryAccess(): Promise<{ uri: string }> {
    console.warn('requestDirectoryAccess is not implemented on web :(');
    return Promise.reject('requestDirectoryAccess is not implemented on web.');
  }

  async getOpusFiles(options: { uri: string }): Promise<{ files: string[] }> {
    console.warn('getOpusFiles is not implemented on web :( ' + options.uri);
    return Promise.reject('getOpusFiles is not implemented on web.');
  }
}
