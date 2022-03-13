package org.keelfy.dndlist.configuration.properties.model;

/**
 * @author e.kuzmin
 * */
public interface MailMessagePropertiesProvider {

    /**
     * Тема письма.
     * */
    String getSubject();

    /**
     * Название файла шаблона.
     * */
    String getMessageTemplateName();

    /**
     * Наименование папки с прикрепляемыми сообщениями.
     * */
    default String getAttachmentDirectory() {
        return getMessageTemplateName();
    }

}
