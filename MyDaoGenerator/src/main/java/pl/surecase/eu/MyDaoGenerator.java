package pl.surecase.eu;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "greendao");
        schema.enableKeepSectionsByDefault();

        Entity box = schema.addEntity("Match");
        box.addIdProperty();
        box.addStringProperty("t1");
        box.addStringProperty("t2");
        box.addStringProperty("t1c");
        box.addStringProperty("t2c");
        box.addLongProperty("ETA");
        box.addBooleanProperty("alarm_set");
        new DaoGenerator().generateAll(schema, args[0]);
    }
}
