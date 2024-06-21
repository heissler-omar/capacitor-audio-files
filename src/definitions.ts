// declare module "@capacitor/core" {
//   interface PluginRegistry {
//     AudioFiles: AudioFilesPlugin;
//   }
// }

export interface AudioFilesPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  showMessage(options: {msg: string}): Promise<{ value: string }>;
  requestDirectoryAccess(): Promise<{ uri: string }>;
  getOpusFiles(options: { uri: string }): Promise<{ files: string[] }>;
}