package project.plugin;

import com.intellij.ide.util.DefaultPsiElementCellRenderer;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.ui.CollectionListModel;
import com.intellij.ui.ToolbarDecorator;
import org.jetbrains.annotations.Nullable;
import java.util.List;

import javax.swing.*;
import java.awt.*;

/**
 * Created by John Smith on 4/19/2017.
 */
public class GenerateDialog extends DialogWrapper {
    private LabeledComponent<JPanel> myComponent;

    public GenerateDialog(boolean isNormal){
        super(true);
        init();
        setTitle("Hastat?");

        JList fieldList=new JList<>();
        ToolbarDecorator decorator= ToolbarDecorator.createDecorator(fieldList);
        decorator.disableAddAction();
        decorator.createPanel();
        JPanel panel=decorator.createPanel();
        if(isNormal){
            myComponent=LabeledComponent.create(panel,"This is smth");
        }

        init();

    }
    /* public  GenerateDialog(PsiClass psiClass) {
         super(psiClass.getProject());
         init();
         setTitle("Select Fields to include in my plugin");
         myFields=new CollectionListModel<>(psiClass.getAllFields());
         PsiFile containingFile = psiClass.getContainingFile();
         JList fieldList=new JList<>(myFields);
         fieldList.setCellRenderer(new DefaultPsiElementCellRenderer());
         ToolbarDecorator decorator= ToolbarDecorator.createDecorator(fieldList);
         decorator.disableAddAction();
         decorator.createPanel();
         JPanel panel=decorator.createPanel();
         myComponent=LabeledComponent.create(panel,"Fields to include in my plugin");
         init();

     }*/
    public GenerateDialog(PsiClass psiClass){
        super(psiClass.getProject());
        init();
        String path= psiClass.getQualifiedName();


    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return myComponent;
    }

    /*public List<PsiField> getFields() {
        return myFields.getItems();
    }*/
}
