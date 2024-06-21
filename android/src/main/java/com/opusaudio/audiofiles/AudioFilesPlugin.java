package com.opusaudio.audiofiles;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;

import androidx.annotation.RequiresApi;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

import com.getcapacitor.Bridge;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;
import android.Manifest;
import com.getcapacitor.PluginMethod;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


@CapacitorPlugin(
    name = "AudioFiles",
    permissions = {
        @Permission(alias = "storage", strings = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            // For Android 13+
            Manifest.permission.READ_MEDIA_AUDIO
        })
    }
)

public class AudioFilesPlugin extends Plugin {

    private static final int REQUEST_CODE_OPEN_DIRECTORY = 1;
    private PluginCall savedCall;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @PluginMethod
    public void requestDirectoryAccess(PluginCall call) {
        savedCall = call;
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(call, intent, REQUEST_CODE_OPEN_DIRECTORY);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void handleOnActivityResult(int requestCode, int resultCode, Intent data) {
        super.handleOnActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_OPEN_DIRECTORY) {
            if (resultCode == getActivity().RESULT_OK && data != null) {
                Uri treeUri = data.getData();
                String path = treeUri.getPath();

                if (path != null && path.contains("primary:Android/media")) {
                    getActivity().getContentResolver().takePersistableUriPermission(treeUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    JSObject result = new JSObject();
                    result.put("uri", treeUri.toString());
                    savedCall.resolve(result);
                } else {
                    savedCall.reject("Please select the 'Android/media' directory.");
                }
            } else {
                savedCall.reject("Directory selection cancelled.");
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @PluginMethod
    public void getOpusFiles(PluginCall call) {
        String uriString = call.getString("uri");
        if (uriString == null) {
            call.reject("Uri not provided");
            return;
        }

        Uri treeUri = Uri.parse(uriString);
        List<String> opusFiles = new ArrayList<>();
        findOpusFiles(treeUri, opusFiles);

        JSArray files = new JSArray();
        for (String file : opusFiles) {
            files.put(file);
        }

        JSObject result = new JSObject();
        result.put("files", files);
        call.resolve(result);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void findOpusFiles(Uri treeUri, List<String> opusFiles) {
        Uri childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(treeUri, DocumentsContract.getTreeDocumentId(treeUri));
        try (Cursor cursor = getActivity().getContentResolver().query(childrenUri, new String[]{
                DocumentsContract.Document.COLUMN_DOCUMENT_ID,
                DocumentsContract.Document.COLUMN_DISPLAY_NAME,
                DocumentsContract.Document.COLUMN_MIME_TYPE
        }, null, null, null)) {
            while (cursor.moveToNext()) {
                String documentId = cursor.getString(0);
                String displayName = cursor.getString(1);
                String mimeType = cursor.getString(2);

                if (mimeType.equals(DocumentsContract.Document.MIME_TYPE_DIR)) {
                    Uri documentUri = DocumentsContract.buildDocumentUriUsingTree(treeUri, documentId);
                    findOpusFiles(documentUri, opusFiles);
                } else {
                    String extension = MimeTypeMap.getFileExtensionFromUrl(displayName);
                    if (extension != null && extension.equalsIgnoreCase("opus")) {
                        Uri documentUri = DocumentsContract.buildDocumentUriUsingTree(treeUri, documentId);
                        opusFiles.add(documentUri.toString());
                    }
                }
            }
        }
    }
}
