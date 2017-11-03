package org.alexdev.icarus.http.template.binders;

import org.alexdev.icarus.http.util.config.Configuration;

public class TemplateClientBinder {

    private String ip;
    private String port;
    private String externalFlashTexts;
    private String externalVariables;
    private String externalOverrideTexts;
    private String externalOverrideVariables;
    private String path;
    private String swf;
    private String productdata;
    private String furnidata;
    private String figuredata;

    public TemplateClientBinder() {
        this.ip = Configuration.CLIENT_IP;
        this.port = Configuration.CLIENT_PORT;
        this.externalVariables = Configuration.CLIENT_EXTERNAL_VARIABLES;
        this.externalFlashTexts = Configuration.CLIENT_EXTERNAL_FLASH_TEXTS;
        this.externalOverrideTexts = Configuration.CLIENT_EXTERNAL_OVERRIDE_TEXTS;
        this.externalOverrideVariables = Configuration.CLIENT_EXTERNAL_OVERRIDE_VARIABLES;
        this.productdata = Configuration.CLIENT_PRODUCTDATA;
        this.furnidata = Configuration.CLIENT_FURNIDATA;
        this.figuredata = Configuration.CLIENT_FIGUREDATA;
        this.path = Configuration.CLIENT_PATH;
        this.swf = Configuration.CLIENT_SWF;
    }
}
