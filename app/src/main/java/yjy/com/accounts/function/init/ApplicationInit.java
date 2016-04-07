package yjy.com.accounts.function.init;

import yjy.com.accounts.control.TagsController;

/**
 * Created by yujinyang on 16/4/7.
 */
public class ApplicationInit {
    public void init(){
        TagsController.getController().initTags();
    }
}
