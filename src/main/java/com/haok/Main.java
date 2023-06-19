package com.haok;

import com.haok.components.TextFiledWithDescribe;
import com.haok.pack.PackConfig;
import com.haok.pack.data.type.ConfigDataType;
import com.haok.pack.data.type.FontDataType;
import com.haok.pack.data.type.MainMenuPictureDataType;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.haok.Config.*;

public class Main {
    public static JFrame frame;
    public static TextFiledWithDescribe describe;
    public static TextFiledWithDescribe packName;
    public static JComboBox<String> version;
    public static TextFiledWithDescribe icon;
    public static TextFiledWithDescribe font;
    public static JTextField fontView;
    public static Font microsoftYaheiFont = null;
    public static PackConfig config = new PackConfig();

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.err.println("Failed to load Look & Feel.");
            Utils.exceptionHandle(e);
        }
        //Create Frame
        frame = new JFrame(TITLE);
        frame.setIconImage(new ImageIcon(Objects.requireNonNull(Main.class.getResource("/com/haok/logo.png"))).getImage());
        //Main Frame
        JTabbedPane pane = new JTabbedPane();
        frame.setContentPane(pane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setLocationRelativeTo(null);  //Windows Center
        frame.setResizable(false);  //Frame Not Resizeable
        String[] fontsName = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        //Set font
        for (String s : fontsName) {
            if (s.equals("微软雅黑")) {
                microsoftYaheiFont = new Font("微软雅黑", Font.PLAIN, 13);
                initGlobalFont(microsoftYaheiFont);
            }
        }
        System.out.println("Finish frame create.");
        //--------PACK CONFIG--------//
        //Set Panel Contents
        JPanel settingPane = new JPanel();
        packName = new TextFiledWithDescribe("资源包名", false, true);
        {
            Document document = packName.getTextField().getDocument();
            //set pack name config when user type in content for pack-name text filed
            document.addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    try {
                        config.put(
                                ConfigDataType.PACK_NAME,
                                e.getDocument().getText(0, e.getDocument().getLength())
                        );
                    } catch (BadLocationException ex) {
                        Utils.exceptionHandle(ex);
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    try {
                        config.put(ConfigDataType.PACK_NAME, e.getDocument().getText(0, e.getDocument().getLength()));
                    } catch (BadLocationException ex) {
                        Utils.exceptionHandle(ex);
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                }
            });
        }
        describe = new TextFiledWithDescribe("描述", false, true);
        {
            Document document = describe.getTextField().getDocument();
            //set pack name config when user type in content for pack-name text filed
            document.addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    try {
                        config.put(
                                ConfigDataType.DESCRIBE,
                                e.getDocument().getText(0, e.getDocument().getLength())
                        );
                    } catch (BadLocationException ex) {
                        Utils.exceptionHandle(ex);
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    try {
                        config.put(ConfigDataType.PACK_NAME, e.getDocument().getText(0, e.getDocument().getLength()));
                    } catch (BadLocationException ex) {
                        Utils.exceptionHandle(ex);
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                }
            });
        }

        version = new JComboBox<>();//create version combobox
        String[] versions =
                //Version
                {"1.13 ~ 1.14.4", "1.15 ~ 1.16.1", "1.16.2 ~ 1.16.5", "1.17 ~ 1.17.1", "1.18 ~ 1.18.2", "1.19 ~ 1.19.2", "1.19.3 ~ 22w44a", "1.19.3 ~ 1.19.4(23w07a)", "1.19.4 ~ 23w13a", "1.20(23w14a) ~ 1.20(23w16a)", "1.20"};
        List<String> versionList = Arrays.asList(versions);
        for (String v : versionList) {
            //Add contents for combobox
            version.addItem(v);
            System.out.println(v);
        }
        config.put(ConfigDataType.VERSION, String.valueOf(4));
        //set version data config when user choose a version number
        version.addItemListener(e -> {
            int index = versionList.indexOf(e.getItem());
            int versionNumber;
            if (index < 6) {
                versionNumber = index + 4;
            } else {
                versionNumber = index + 5;
            }
            config.put(ConfigDataType.VERSION, String.valueOf(versionNumber));
        });
        //combobox panel
        JPanel comboPanel = new JPanel(new BorderLayout());
        comboPanel.setPreferredSize(new Dimension(300, 50));
        comboPanel.add(new JLabel("版本"), BorderLayout.NORTH);
        comboPanel.add(version, BorderLayout.CENTER);
        //icon preview
        JLabel iconLabel = new JLabel();
        ImageIcon iconView = new ImageIcon();
        //icon image text filed
        icon = new TextFiledWithDescribe("图标", true, false);
        icon.setChooserFilter(PNG_FILE_FILTER);
        //set icon image preview label when choose an image
        icon.setSelectFileListener(f -> {
            //read image
            BufferedImage image = ImageIO.read(f);
            System.out.println("Read an image:" + icon.getText());
            //determine image is a square
            if (image.getWidth() == image.getHeight()) {
                //set image
                System.out.println("Set an image:" + image);
                iconView.setImage(image);
                iconView.setImage(iconView.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
                iconLabel.setIcon(iconView);
                //set image config when user choose an icon image
                config.put(ConfigDataType.ICON, f.getAbsolutePath());
            } else {
                icon.doRemove();
                JOptionPane.showMessageDialog(frame, "图片必须为正方形", "", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        icon.setPreferredSize(new Dimension(300, 100));
        iconLabel.setPreferredSize(new Dimension(50, 50));
        icon.add(iconLabel, BorderLayout.SOUTH);
        icon.setRemoveFileListener(() -> iconLabel.setIcon(null));
        //add contents for config panel
        settingPane.add(packName);
        settingPane.add(describe);
        settingPane.add(comboPanel);
        settingPane.add(icon);
        pane.addTab("资源包选项", settingPane);
        System.out.println("Finish Config UI set.");
        //--------FONT--------//
        JPanel fontPanel = new JPanel();
        JRadioButton chooseFontFile = new JRadioButton("选择字体文件");
        JRadioButton chooseSystemFont = new JRadioButton("选择系统字体");
        ButtonGroup group = new ButtonGroup();
        group.add(chooseFontFile);
        group.add(chooseSystemFont);
        //font choose text filed
        font = new TextFiledWithDescribe("字体", true, false);
        font.setChooserFilter(TTF_FILE_FILTER);
        fontView = new JTextField("A quickly brown fox jumps over a lazy dog.");
        JLabel fontViewLabel = new JLabel("预览:");
        JComboBox<String> fonts = new JComboBox<>();
        //font files list
        List<File> fontFiles = Utils.getSystemFonts();
        //font objects list
        List<Font> fontList = new ArrayList<>();
        fontFiles.forEach(file -> {
            try {
                fontList.add(Font.createFont(Font.TRUETYPE_FONT, file));
            } catch (FontFormatException | IOException e) {
                Utils.exceptionHandle(e);
            }
        });
        //font names list
        List<String> fontsNameList = new ArrayList<>();
        fontList.forEach(font1 -> fontsNameList.add(font1.getName()));
        fontsNameList.forEach(fonts::addItem);
        fontView.setFont(microsoftYaheiFont != null ? microsoftYaheiFont.deriveFont(20f) : new Font("Arial", Font.PLAIN, 20));
        font.setSelectFileListener(file -> {
            //font preview
            Font f = Font.createFont(Font.TRUETYPE_FONT, file);
            System.out.println("Read a font:" + f.getName());
            f = f.deriveFont(20.0f);
            fontView.setFont(f);
            //add font pack-config
            String fontName = f.getFamily();
            fontName = fontName.toLowerCase();
            fontName = fontName.replace(' ', '_');
            config.put(FontDataType.FILE_PATH, file.getAbsolutePath());
            config.put(FontDataType.NAME, fontName);
        });
        font.setRemoveFileListener(() -> {
            if (microsoftYaheiFont != null) fontView.setFont(microsoftYaheiFont.deriveFont(20f));
        });
        chooseSystemFont.addItemListener(e -> {
            font.setVisible(!font.isVisible());
            fonts.setVisible(true);
        });
        chooseFontFile.addItemListener(e -> {
            fonts.setVisible(!fonts.isVisible());
            font.setVisible(true);
        });
        fonts.addItemListener(e -> {
            // choose system font preview
            int index = fontsNameList.indexOf(e.getItem());
            Font f = fontList.get(index).deriveFont(20f);
            System.out.println("Read a font:" + f.getName());
            f = f.deriveFont(20.0f);
            fontView.setFont(f);
            File file = fontFiles.get(index);
            config.put(FontDataType.FILE_PATH, file.getAbsolutePath());
            String fontName = f.getFamily().toLowerCase(Locale.CHINA).replace(' ', '_');
            String regEx = "[^a-zA-Z]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(fontName);
            boolean notAllLetter = m.find();
            if (notAllLetter) {
                config.put(FontDataType.NAME, "font");
            } else {
                config.put(FontDataType.NAME, fontName);
            }
        });
        chooseFontFile.doClick();

        JPanel fontChoosePanel = new JPanel(new GridLayout(6, 1));
        fontChoosePanel.add(chooseFontFile);
        fontChoosePanel.add(font);
        fontChoosePanel.add(chooseSystemFont);
        fontChoosePanel.add(fonts);

        fontChoosePanel.add(fontViewLabel);
        fontChoosePanel.add(fontView);
        fontPanel.add(fontChoosePanel);
        pane.addTab("字体", fontPanel);
        System.out.println("Finish Font UI set.");
        //--------MAIN MENU PICTURE--------//

        /*
        main menu picture choose text filed
        1.create object
        2.add tip text
        3.add on mouse listener
        4.set select file listener
        */

        TextFiledWithDescribe front = new TextFiledWithDescribe("前    /tp @p ~ ~ ~ 0 0", true, false);
        front.getDescribe().addMouseListener(new CopyHandle("/tp @p ~ ~ ~ 0 0"));
        front.getDescribe().setToolTipText("点击复制指令");
        front.setChooserFilter(PNG_FILE_FILTER);
        front.setSelectFileListener(file -> {
            System.out.println("Read an image:\n" + file.getAbsolutePath());
            config.put(MainMenuPictureDataType.FRONT, file.getAbsolutePath());
        });

        TextFiledWithDescribe behind = new TextFiledWithDescribe("后    /tp @p ~ ~ ~ 180 0", true, false);
        behind.getDescribe().addMouseListener(new CopyHandle("/tp @p ~ ~ ~ 180 0"));
        behind.getDescribe().setToolTipText("点击复制指令");
        behind.setChooserFilter(PNG_FILE_FILTER);
        behind.setSelectFileListener(file -> {
            System.out.println("Read an image:\n" + file.getAbsolutePath());
            config.put(MainMenuPictureDataType.BEHIND, file.getAbsolutePath());
        });

        TextFiledWithDescribe left = new TextFiledWithDescribe("左    /tp @p ~ ~ ~ -90 0", true, false);
        left.setChooserFilter(PNG_FILE_FILTER);
        left.getDescribe().addMouseListener(new CopyHandle("/tp @p ~ ~ ~ -90 0"));
        left.getDescribe().setToolTipText("点击复制指令");
        left.setSelectFileListener(file -> {
            System.out.println("Read an image:\n" + file.getAbsolutePath());
            config.put(MainMenuPictureDataType.LEFT, file.getAbsolutePath());
        });

        TextFiledWithDescribe right = new TextFiledWithDescribe("右    /tp @p ~ ~ ~ 90 0", true, false);
        right.setChooserFilter(PNG_FILE_FILTER);
        right.addMouseListener(new CopyHandle("/tp @p ~ ~ ~ 90 0"));
        right.getDescribe().setToolTipText("点击复制指令");
        right.setSelectFileListener(file -> {
            System.out.println("Read an image:\n" + file.getAbsolutePath());
            config.put(MainMenuPictureDataType.RIGHT, file.getAbsolutePath());
        });

        TextFiledWithDescribe up = new TextFiledWithDescribe("上    /tp @p ~ ~ ~ -90 -90", true, false);
        up.setChooserFilter(PNG_FILE_FILTER);
        up.getDescribe().addMouseListener(new CopyHandle("/tp @p ~ ~ ~ -90 -90"));
        up.getDescribe().setToolTipText("点击复制指令");
        up.setSelectFileListener(file -> {
            System.out.println("Read an image:\n" + file.getAbsolutePath());
            config.put(MainMenuPictureDataType.UP, file.getAbsolutePath());
        });

        TextFiledWithDescribe down = new TextFiledWithDescribe("下    /tp @p ~ ~ ~ -90 90", true, false);
        down.setChooserFilter(PNG_FILE_FILTER);
        down.getDescribe().addMouseListener(new CopyHandle("/tp @p ~ ~ ~ -90 90"));
        down.getDescribe().setToolTipText("点击复制指令");
        down.setSelectFileListener(file -> {
            System.out.println("Read an image:\n" + file.getAbsolutePath());
            config.put(MainMenuPictureDataType.DOWN, file.getAbsolutePath());
        });

        //picture over background

        TextFiledWithDescribe pictureOver = new TextFiledWithDescribe("覆盖图片", true, false);
        pictureOver.setChooserFilter(PNG_FILE_FILTER);
        pictureOver.setSelectFileListener(file -> {
            System.out.println("Read an image:\n" + file.getAbsolutePath());
            config.put(MainMenuPictureDataType.OVER, file.getAbsolutePath());
        });

        JPanel mainMenuBackgroundPanel = new JPanel(new GridLayout(7, 1, 0, 10));
        mainMenuBackgroundPanel.add(front);
        mainMenuBackgroundPanel.add(behind);
        mainMenuBackgroundPanel.add(left);
        mainMenuBackgroundPanel.add(right);
        mainMenuBackgroundPanel.add(up);
        mainMenuBackgroundPanel.add(down);
        mainMenuBackgroundPanel.add(pictureOver);
        ScrollPane mainMenuScroll = new ScrollPane();
        mainMenuScroll.add(mainMenuBackgroundPanel);
        pane.addTab("主菜单全景图", mainMenuScroll);
        System.out.println("Finish Menu Background Photo UI set.");
        //--------MAKE--------
        JPanel savePanel = new JPanel();
        TextFiledWithDescribe packPath = new TextFiledWithDescribe("保存路径", true, false);
        packPath.isShowSaveDialog(true);
        JCheckBox isZip = new JCheckBox("制作为Zip压缩文件。");
        isZip.setPreferredSize(new Dimension(300,25));
        isZip.addActionListener(e -> {
            if (isZip.isSelected()) {
                System.out.println("Is zip!");
            } else {
                System.out.println("Not zip!");
            }
        });
        JProgressBar progressBar = new JProgressBar();
        progressBar.setPreferredSize(new Dimension(300,25));
        progressBar.setString("制作进度");
        savePanel.add(packPath);
        savePanel.add(isZip);
        savePanel.add(progressBar);
        pane.addTab("保存", savePanel);
        //set frame visible
        frame.setVisible(true);
    }

    /**
     * Set app font
     *
     * @param font a font
     */
    private static void initGlobalFont(Font font) {
        FontUIResource fontRes = new FontUIResource(font);
        for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements(); ) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontRes);
            }
        }
    }
}

/**
 * Set clipboard when mouse click.
 */
class CopyHandle implements MouseListener {
    private final String data;

    /**
     * @param data Copy string content.
     */
    public CopyHandle(String data) {
        this.data = data;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!(e.getButton() == MouseEvent.BUTTON1)) return;
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection string = new StringSelection(data);
        clipboard.setContents(string, null);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}