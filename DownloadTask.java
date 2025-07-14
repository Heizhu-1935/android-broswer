 com.example.watchbrowser;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.webkit.MimeTypeMap;

public class DownloadTask extends AsyncTask<String, Void, Void> {
    private Context context;
    
    public DownloadTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... params) {
        String url = params;
        String mimeType = params^1^;
        
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setMimeType(mimeType);
        
        // 根据类型设置保存路径
        if(mimeType.startsWith("image/")) {
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_PICTURES, "WatchBrowser/"+System.currentTimeMillis()+".jpg");
        } else if(mimeType.startsWith("video/")) {
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_MOVIES, "WatchBrowser/"+System.currentTimeMillis()+".mp4");
        } else if(mimeType.equals("application/vnd.android.package-archive")) {
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS, "WatchBrowser/app_"+System.currentTimeMillis()+".apk");
        }
        
        DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        dm.enqueue(request);
        return null;
    }
}
