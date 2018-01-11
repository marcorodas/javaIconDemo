package pe.assupport.javaicondemo;

import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.GlyphIcons;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import de.jensd.fx.glyphs.weathericons.WeatherIcon;
import de.jensd.fx.glyphs.weathericons.WeatherIconView;
import lombok.Getter;

/**
 *
 * @author skynet
 */
public enum IconType {
    FONTAWESOME("FontAwesome"),
    MATERIALDESIGN("MaterialDesign"),
    WEATHERICON("WeatherIcon");

    @Getter
    private final String name;

    private IconType(String name) {
        this.name = name;
    }

    public GlyphIcon<?> getGlyph(String glyphName) {
        switch (this) {
            case FONTAWESOME:
                return new FontAwesomeIconView(FontAwesomeIcon.valueOf(glyphName));
            case MATERIALDESIGN:
                return new MaterialDesignIconView(MaterialDesignIcon.valueOf(glyphName));
            case WEATHERICON:
                return new WeatherIconView(WeatherIcon.valueOf(glyphName));
            default:
                return null;
        }
    }

    public GlyphIcons[] getGlyphIcons() {
        switch (this) {
            case FONTAWESOME:
                return FontAwesomeIcon.values();
            case MATERIALDESIGN:
                return MaterialDesignIcon.values();
            case WEATHERICON:
                return WeatherIcon.values();
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return this.getName();
    }

}
