
package  ${relativePackage};


import android.os.Build;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import ${applicationPackage}.R;
import com.yuan.basemodule.ui.base.mvp.MVPActivity;

/**
 * create by Yuan ye.
 * download truck before use this
 * 主要用于简化WebView Activity的创建
 */
public class ${activityClass} extends MVPActivity{

	private WebView mWebView;

    @Override
    protected void initData(Bundle savedInstanceState) {
		getTitleBar().setLeftIcon(R.drawable.ic_base_back_white)
                .setToolbar("公告详情");
        String content = getIntent().getStringExtra("webUrl");
		initWebView(content);
	}

	 private void initWebView(String content) {
        mWebView = (WebView) findViewById(R.id.webView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //设置文字自动排版
            mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }
        //启用支持javascript
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        // 设置是否可缩放
        settings.setBuiltInZoomControls(true);

        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                imgReset(mWebView);
            }
        });

        mWebView.loadData(content, "text/html; charset=UTF-8", null);
    }

    /**
     * 刷新webView
     */
    public void refresh(String content) {
        mWebView.clearHistory();
        mWebView.clearFormData();
        mWebView.clearMatches();
        mWebView.clearCache(true);
        mWebView.loadData(content, "text/html; charset=UTF-8", null);
        mWebView.reload();
    }

    /**
     * 解决webView图片过大的问题
     *
     * @param content
     */
    private void imgReset(WebView content) {
        content.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                " img.style.maxWidth = '100%';img.style.height='auto';" +
                "}" +
                "})()");
    }
	
    @Override
    public int getLayoutId() {
        return R.layout.${activityLayoutName};
    }
}
