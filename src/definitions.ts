export interface AudioFilesPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
