package drzhark.guiapi.widget;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.model.SimpleButtonModel;
import drzhark.guiapi.GuiModScreen;
import drzhark.guiapi.ModSettingScreen;
import drzhark.guiapi.setting.SettingBoolean;

/**
 * This is the Widget for boolean settings. It uses a button to display to the
 * user.
 *
 * @author lahwran
 */
public class WidgetBoolean extends WidgetSetting implements Runnable {

    /**
     * The reference to the underlying Button.
     */
    public Button button;
    /**
     * The text to display on the button when the setting is false.
     */
    public String falseText;
    /**
     * The reference to the SettingBoolean that this WidgetBoolean uses.
     */
    public SettingBoolean settingReference = null;
    /**
     * The text to display on the button when the setting is true.
     */
    public String trueText;

    /**
     * This creates a new WidgetBoolean using the SettingBoolean and String
     * provided. It uses 'true' and 'false' for the text.
     *
     * @param setting The backing setting.
     * @param title The title for this Widget. It is what will show on the
     *        button, asides from it's current value.
     */
    public WidgetBoolean(SettingBoolean setting, String title) {
        this(setting, title, "true", "false");
    }

    /**
     * This creates a new WidgetBoolean using the WidgetBoolean and String
     * provided, as well as setting the true and false text.
     *
     * @param setting The backing setting.
     * @param title The title for this Widget. It is what will show on the
     *        button, asides from it's current value.
     * @param truetext The text to display what the setting is true.
     * @param falsetext The text to display what the setting is false.
     */
    public WidgetBoolean(SettingBoolean setting, String title, String truetext, String falsetext) {
        super(title);
        setTheme("");
        this.trueText = truetext;
        this.falseText = falsetext;
        SimpleButtonModel bmodel = new SimpleButtonModel();
        this.button = new Button(bmodel);
        bmodel.addActionCallback(this);
        add(this.button);
        this.settingReference = setting;
        this.settingReference.displayWidget = this;
        update();
    }

    @Override
    public void addCallback(Runnable paramRunnable) {
        this.button.getModel().addActionCallback(paramRunnable);
    }

    @Override
    public void removeCallback(Runnable paramRunnable) {
        this.button.getModel().removeActionCallback(paramRunnable);
    }

    @Override
    public void run() {
        if (this.settingReference != null) {
            this.settingReference.set(!this.settingReference.get(ModSettingScreen.guiContext), ModSettingScreen.guiContext);
        }
        update();
        GuiModScreen.clicksound();
    }

    @Override
    public void update() {
        this.button.setText(userString());
    }

    @Override
    public String userString() {
        if (this.settingReference != null) {
            if (this.niceName.length() > 0) {
                return String
                        .format("%s: %s", this.niceName, this.settingReference.get(ModSettingScreen.guiContext) ? this.trueText : this.falseText);
            } else {
                return this.settingReference.get(ModSettingScreen.guiContext) ? this.trueText : this.falseText;
            }
        } else {
            if (this.niceName.length() > 0) {
                return String.format("%s: %s", this.niceName, "no value");
            } else {
                return "no value or title";
            }
        }
    }
}
