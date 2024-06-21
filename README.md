# capacitor-audio-files

This plugin allows to access audio files using MediaStore

## Install

```bash
npm install capacitor-audio-files
npx cap sync
```

## API

<docgen-index>

* [`echo(...)`](#echo)
* [`showMessage(...)`](#showmessage)
* [`requestDirectoryAccess()`](#requestdirectoryaccess)
* [`getOpusFiles(...)`](#getopusfiles)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => Promise<{ value: string; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


### showMessage(...)

```typescript
showMessage(options: { msg: string; }) => Promise<{ value: string; }>
```

| Param         | Type                          |
| ------------- | ----------------------------- |
| **`options`** | <code>{ msg: string; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


### requestDirectoryAccess()

```typescript
requestDirectoryAccess() => Promise<{ uri: string; }>
```

**Returns:** <code>Promise&lt;{ uri: string; }&gt;</code>

--------------------


### getOpusFiles(...)

```typescript
getOpusFiles(options: { uri: string; }) => Promise<{ files: string[]; }>
```

| Param         | Type                          |
| ------------- | ----------------------------- |
| **`options`** | <code>{ uri: string; }</code> |

**Returns:** <code>Promise&lt;{ files: string[]; }&gt;</code>

--------------------

</docgen-api>
