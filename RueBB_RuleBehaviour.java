package com.opentext.rules;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.opentext.accelerators.ApplicationFactory;
import com.opentext.accelerators.AssertionUtilities;
import com.opentext.accelerators.ReusableMethods;
import com.opentext.commonutils.EntityCommonUtils;
import com.opentext.commonvariables.EntityCommonVariables;
import com.opentext.commonvariables.RulesCommonVariables;
import com.opentext.entity.buildingblock.detailview.LayoutPropertiesView.PanelType;
import com.opentext.entity.buildingblock.detailview.PropertyPropertiesView;
import com.opentext.entity.buildingblock.detailview.PropertyPropertiesView.PropertyType;
import com.opentext.entity.buildingblock.editorview.FormEditorView;
import com.opentext.entity.buildingblock.editorview.RuleEditorView;
import com.opentext.entity.buildingblock.editorview.RuleEditorView.Then;
import com.opentext.entity.buildingblock.editorview.RuleEditorView.ruleTrigger;
import com.opentext.objectrepository.processexperience.PEFormElements;
import com.opentext.objectrepository.processexperience.PEFormElements.PEFormTypes;
import com.opentext.workflow.entity.EntityNative;
import com.opentext.workflow.platform.CWSDocument;
import com.opentext.workflow.platform.OrganizeWorkspaces;
import com.opentext.workflow.processexperience.PECommonFunctions;
import com.opentext.workflow.processexperience.PECommonFunctions.CreateEntity;
import com.opentext.workflow.processexperience.PEForm;

public class RueBB_RuleBehaviour extends EntityCommonUtils {
	public static boolean sessionFlag = false;
    
	@BeforeMethod(timeOut = 20 * 60 * 1000)
	public void preRequisite() throws InterruptedException {
		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("Execution of Gaps preRequisite");
		if(sessionFlag==false) {
			ApplicationFactory.launchProcessPlatform();
			wsd = new OrganizeWorkspaces();
			cwsdoc = new CWSDocument();
			entityNative = new EntityNative();
			formEditorView = new FormEditorView();
			ruleEditorView = new RuleEditorView();
			propertiesView = new PropertyPropertiesView();
			ruleEditorView =  new RuleEditorView();
			sessionFlag=true;
		}
	}
	
	/*
	 * [PP.10317] Verify the rule behavior on creating a rule with "A property changes" which starts the process When a property in a condition is changed on lost focus of form
	 */
	@Test(timeOut = 20 * 60 * 1000)
	public void pp10317Verifywhetherconditionisperformed() throws InterruptedException {
 		
 		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("Starting Execution of test case pp10317Verifyactionbutton. To Verify When a property in a condition is changed on lost focus of form");
  		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("PP.102317---Creating Rule to start specified process when property in a condition is changed on lost focus of form");
  		createAndPublishBPM("SampleBPM3",RulesCommonVariables.RULEBBADVANCEDEXPRESSIONWS, RulesCommonVariables.RULEBBADVANCEDEXPRESSIONPROJECT,RulesCommonVariables.RULESFOLDERNAME9);
 		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("BPM has been created and published successfully");
 		
 		createOrGoToEntity(RulesCommonVariables.ENTITY10317,RulesCommonVariables.RULEBBADVANCEDEXPRESSIONWS, RulesCommonVariables.RULEBBADVANCEDEXPRESSIONPROJECT,RulesCommonVariables.RULESFOLDERNAME9);
 		createProperty(RulesCommonVariables.ATextProperty,RulesCommonVariables.ATextProperty, PropertyType.TYPE_TEXT);
 		createProperty(RulesCommonVariables.ABoolProperty, RulesCommonVariables.ABoolProperty, PropertyType.TYPE_BOOL);
 		createProperty(RulesCommonVariables.AIntProperty,RulesCommonVariables.AIntProperty, PropertyType.TYPE_INTEGER);
 		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("Properties are added");
 		
 		addListDetails(RulesCommonVariables.list10317, RulesCommonVariables.list10317, RulesCommonVariables.LISTCATEGORY, null, false, "200",null);
 		selectAndConfigureAnyBuildingBlock(EntityCommonVariables.LISTBBPARENT, RulesCommonVariables.list10317);
 		addPropertiesToListWithSettings(RulesCommonVariables.ATextProperty, false,null, null, null);
 		addPropertiesToListWithSettings(RulesCommonVariables.ABoolProperty, false,null, null, null);
 		addPropertiesToListWithSettings(RulesCommonVariables.AIntProperty, false,null, null, null);
 		saveAndCloseListEditor(true);
 		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("List is added and configured.");
 		
 		String[] condition = { "AInt" };
 		addRule("BPMRule","BPMRule");
 		selectAndConfigureAnyBuildingBlock(EntityCommonVariables.RULEBBPARENT, "BPMRule");
 		setWhenWithCondition(ruleTrigger.TYPE_PROPERTYCHANGE, null, RulesCommonVariables.IFCONDITIONONE, condition, RulesCommonVariables.SELECTCONDITIONLESSTHAN2);
 		startProcessRule(Then.STARTPROCESS, "SampleBPM3", "AText");
 		saveRuleEditor();
 		entityNative.save();		
 		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("Rules is added and configured.");
 		
 		addFormDetails(RulesCommonVariables.createForm, RulesCommonVariables.createForm, RulesCommonVariables.createForm);
 		selectAndConfigureAnyBuildingBlock(EntityCommonVariables.FORMBBPARENT, RulesCommonVariables.createForm);
 		formEditorView.dragAndDropProperty(RulesCommonVariables.ATextProperty, 20, 15);
 		formEditorView.dragAndDropProperty(RulesCommonVariables.ABoolProperty, 40,15);
 		formEditorView.dragAndDropProperty(RulesCommonVariables.AIntProperty, 60,15);
 		saveAndCloseFormEditor();
 		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("CreateForm is created and configured.");
 		
 		addFormDetails(RulesCommonVariables.defaultForm, RulesCommonVariables.defaultForm, RulesCommonVariables.defaultForm);
 		selectAndConfigureAnyBuildingBlock(EntityCommonVariables.FORMBBPARENT, RulesCommonVariables.defaultForm);
 		formEditorView.dragAndDropProperty(RulesCommonVariables.ATextProperty, 20, 15);
 		formEditorView.dragAndDropProperty(RulesCommonVariables.ABoolProperty, 40,15);
 		formEditorView.dragAndDropProperty(RulesCommonVariables.AIntProperty, 60,15);
 		saveAndCloseFormEditor();
 		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("DefaultForm is created and configured.");
		
		addLayoutDetails(RulesCommonVariables.FocusLayout, RulesCommonVariables.FocusLayout, RulesCommonVariables.FocusLayout, true, true, true);
		selectAndConfigureAnyBuildingBlock(EntityCommonVariables.LAYOUTBBPARENT, RulesCommonVariables.FocusLayout);
		dragAndDrop(PanelType.BREADCRUMBS, 40,40);
		dragAndDrop(PanelType.FORM, 20,20);
		addPanelProperties(RulesCommonVariables.createForm,"Full", RulesCommonVariables.createForm);
		layoutEditorSaveAndClose(true);
		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("layout is added and configured."); 
		saveAndCloseEntity();      
	    cwsdoc.publishToOrganization(RulesCommonVariables.ENTITY10317);
	    Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("Entity is published succesfully.");
	    
	    /*
	     * provide Solution Security in adminpage 
	     */
	    provideSolutionSecurityInAdminPage(RulesCommonVariables.RULEBBADVANCEDEXPRESSIONPROJECT);
	    
	    /*
	     * Run Time
	     */
	    ApplicationFactory.launchProcessExperienceURL();
        PECommonFunctions.selectHomePageLayout("Home Page");
        Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("Creating an instance of the entity");       
        PECommonFunctions.selectEntityToCreateInstance(RulesCommonVariables.ENTITY10317);
        Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("Created and opened the Instance.");
        PECommonFunctions.clickCreateButton(CreateEntity.CREATE_AND_OPEN,RulesCommonVariables.ENTITY10317);
        PEForm.setInputTextInTextFields(RulesCommonVariables.AIntProperty,"5", PEFormTypes.DEFAULT);
        PECommonFunctions.saveChangesInPE(PEFormElements.textSetInTextFields(RulesCommonVariables.ATextProperty));
        ReusableMethods.customSleep(3);
        AssertionUtilities.assertTextComparison(RulesCommonVariables.ATextProperty, "", false, "moving the mouse test case passed");
        Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("moving the mouse is set as Expected");
	}
	
	/*
	 *   [PP.10318] Verify the rule behavior on creating a rule with "A property changes" which starts the process "When a property in a condition is changed" "on lost focus of property"
	 */
	@Test(timeOut = 20 * 60 * 1000)
	public void pp10318Verifywhetherconditionischanged() throws InterruptedException {
		
		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("Starting Execution of test case pp10318Verifywhetherconditionischanged. To Verify When a property in a condition is changed on lost focus of form");
 		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("PP.102318---Creating Rule to start specified process when property in a condition is changed on lost focus of form");
 		createAndPublishBPM("SampleBPM4",RulesCommonVariables.RULEBBADVANCEDEXPRESSIONWS, RulesCommonVariables.RULEBBADVANCEDEXPRESSIONPROJECT,RulesCommonVariables.RULESFOLDERNAME9);
		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("SamplePBM4 has been created and published successfully");
		
		createOrGoToEntity(RulesCommonVariables.ENTITY10317,RulesCommonVariables.RULEBBADVANCEDEXPRESSIONWS, RulesCommonVariables.RULEBBADVANCEDEXPRESSIONPROJECT,RulesCommonVariables.RULESFOLDERNAME9);
		createProperty(RulesCommonVariables.ATextProperty,RulesCommonVariables.ATextProperty, PropertyType.TYPE_TEXT);
		createProperty(RulesCommonVariables.ABoolProperty, RulesCommonVariables.ABoolProperty, PropertyType.TYPE_BOOL);
		createProperty(RulesCommonVariables.AIntProperty,RulesCommonVariables.AIntProperty, PropertyType.TYPE_INTEGER);
		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("Properties are added");
		
		addListDetails(RulesCommonVariables.list10317, RulesCommonVariables.list10317, RulesCommonVariables.LISTCATEGORY, null, false, "200",null);
		selectAndConfigureAnyBuildingBlock(EntityCommonVariables.LISTBBPARENT, RulesCommonVariables.list10317);
		addPropertiesToListWithSettings(RulesCommonVariables.ATextProperty, false,null, null, null);
		addPropertiesToListWithSettings(RulesCommonVariables.ABoolProperty, false,null, null, null);
		addPropertiesToListWithSettings(RulesCommonVariables.AIntProperty, false,null, null, null);
		saveAndCloseListEditor(true);
		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("List is added and configured.");
		
		String[] condition = { "AInt" };
		addRule("BPMRule","BPMRule");
		selectAndConfigureAnyBuildingBlock(EntityCommonVariables.RULEBBPARENT, "BPMRule");
		setWhenWithCondition(ruleTrigger.TYPE_PROPERTYCHANGE, null, RulesCommonVariables.IFCONDITIONONE, condition, RulesCommonVariables.SELECTCONDITIONLESSTHAN2);
		startProcessRule(Then.STARTPROCESS, "SampleBPM4", "AText");
		saveRuleEditor();
		entityNative.save();		
		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("Rules is added and configured.");
		
		addFormDetails(RulesCommonVariables.createForm, RulesCommonVariables.createForm, RulesCommonVariables.createForm);
		selectAndConfigureAnyBuildingBlock(EntityCommonVariables.FORMBBPARENT, RulesCommonVariables.createForm);
		formEditorView.dragAndDropProperty(RulesCommonVariables.ATextProperty, 20, 15);
		formEditorView.dragAndDropProperty(RulesCommonVariables.ABoolProperty, 40,15);
		formEditorView.dragAndDropProperty(RulesCommonVariables.AIntProperty, 60,15);
		saveAndCloseFormEditor();
		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("CreateForm is created and configured.");
		
		addFormDetails(RulesCommonVariables.defaultForm, RulesCommonVariables.defaultForm, RulesCommonVariables.defaultForm);
		selectAndConfigureAnyBuildingBlock(EntityCommonVariables.FORMBBPARENT, RulesCommonVariables.defaultForm);
		formEditorView.dragAndDropProperty(RulesCommonVariables.ATextProperty, 20, 15);
		formEditorView.dragAndDropProperty(RulesCommonVariables.ABoolProperty, 40,15);
		formEditorView.dragAndDropProperty(RulesCommonVariables.AIntProperty, 60,15);
		saveAndCloseFormEditor();
		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("DefaultForm is created and configured.");
		
		addLayoutDetails(RulesCommonVariables.FocusLayout, RulesCommonVariables.FocusLayout, RulesCommonVariables.FocusLayout, true, true, true);
		selectAndConfigureAnyBuildingBlock(EntityCommonVariables.LAYOUTBBPARENT, RulesCommonVariables.FocusLayout);
		dragAndDrop(PanelType.BREADCRUMBS, 40,40);
		dragAndDrop(PanelType.FORM, 20,20);
		addPanelProperties(RulesCommonVariables.createForm,"Full", RulesCommonVariables.createForm);
		layoutEditorSaveAndClose(true);
		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("layout is added and configured."); 
		saveAndCloseEntity();      
	    cwsdoc.publishToOrganization(RulesCommonVariables.ENTITY10318);
	    Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("Entity is published succesfully.");
	    
	    /*
	     * provide Solution Security in adminpage 
	     */
	    provideSolutionSecurityInAdminPage(RulesCommonVariables.RULEBBADVANCEDEXPRESSIONPROJECT);
	    
	    /*
	     * Run Time
	     */
	    ApplicationFactory.launchProcessExperienceURL();
        PECommonFunctions.selectHomePageLayout("Home Page");
        Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("Creating an instance of the entity");       
        PECommonFunctions.selectEntityToCreateInstance(RulesCommonVariables.ENTITY10317);
        Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("Created and opened the Instance.");
        PECommonFunctions.clickCreateButton(CreateEntity.CREATE_AND_OPEN,RulesCommonVariables.ENTITY10317);
        PEForm.setInputTextInTextFields(RulesCommonVariables.AIntProperty,"5", PEFormTypes.DEFAULT);
        PECommonFunctions.saveChangesInPE(PEFormElements.textSetInTextFields(RulesCommonVariables.ATextProperty));
        ReusableMethods.customSleep(3);
        AssertionUtilities.assertTextComparison(RulesCommonVariables.ATextProperty, "", false, "moving the mouse test case passed");
        Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("moving the mouse is set as Expected");
	}
	
	/*
	 *   [PP.10319] Verify the rule behavior on creating a rule with "A property changes" which starts the process "When the condition changes to true" "on lost focus of form"
	 */
	@Test(timeOut = 20 * 60 * 1000)
	public void pp10319VerifywhetherBoolischanged() throws InterruptedException {
		
		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("Starting Execution of test case pp10319VerifyBoolischanged. To Verify When a property in a condition is changed on lost focus of form");
 		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("PP.102319---Creating Rule to start specified process when property in a condition is changed on lost focus of form");
 		createAndPublishBPM("SampleBPM1",RulesCommonVariables.RULEBBADVANCEDEXPRESSIONWS, RulesCommonVariables.RULEBBADVANCEDEXPRESSIONPROJECT,RulesCommonVariables.RULESFOLDERNAME9);
		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("SampleBPM1 has been created and published successfully");
		
		createOrGoToEntity(RulesCommonVariables.ENTITY10319,RulesCommonVariables.RULEBBADVANCEDEXPRESSIONWS, RulesCommonVariables.RULEBBADVANCEDEXPRESSIONPROJECT,RulesCommonVariables.RULESFOLDERNAME9);
		createProperty(RulesCommonVariables.ATextProperty,RulesCommonVariables.ATextProperty, PropertyType.TYPE_TEXT);
		createProperty(RulesCommonVariables.ABoolProperty, RulesCommonVariables.ABoolProperty, PropertyType.TYPE_BOOL);
		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("Properties are added");
		
		addListDetails(RulesCommonVariables.list10319, RulesCommonVariables.list10319, RulesCommonVariables.LISTCATEGORY, null, false, "200",null);
		selectAndConfigureAnyBuildingBlock(EntityCommonVariables.LISTBBPARENT, RulesCommonVariables.list10319);
		addPropertiesToListWithSettings(RulesCommonVariables.ATextProperty, false,null, null, null);
		addPropertiesToListWithSettings(RulesCommonVariables.ABoolProperty, false,null, null, null);
		saveAndCloseListEditor(true);
		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("List is added and configured.");
		
		String[] condition = { "ABool" };
		addRule("BPMRule","BPMRule");
		selectAndConfigureAnyBuildingBlock(EntityCommonVariables.RULEBBPARENT, "BPMRule");
		setWhenWithCondition(ruleTrigger.TYPE_PROPERTYCHANGE, null, RulesCommonVariables.IFCONDITIONONE, condition, RulesCommonVariables.SELECTCONDITIONEQUAL10);
		startProcessRule(Then.STARTPROCESS, "SampleBPM1", "AText");
		saveRuleEditor();
		entityNative.save();		
		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("Rules is added and configured.");
		
		addFormDetails(RulesCommonVariables.createForm, RulesCommonVariables.createForm, RulesCommonVariables.createForm);
		selectAndConfigureAnyBuildingBlock(EntityCommonVariables.FORMBBPARENT, RulesCommonVariables.createForm);
		formEditorView.dragAndDropProperty(RulesCommonVariables.ATextProperty, 20, 15);
		formEditorView.dragAndDropProperty(RulesCommonVariables.ABoolProperty, 40,15);
		saveAndCloseFormEditor();
		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("CreateForm is created and configured.");
		
		addFormDetails(RulesCommonVariables.defaultForm, RulesCommonVariables.defaultForm, RulesCommonVariables.defaultForm);
		selectAndConfigureAnyBuildingBlock(EntityCommonVariables.FORMBBPARENT, RulesCommonVariables.defaultForm);
		formEditorView.dragAndDropProperty(RulesCommonVariables.ATextProperty, 20, 15);
		formEditorView.dragAndDropProperty(RulesCommonVariables.ABoolProperty, 40,15);
		saveAndCloseFormEditor();
		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("DefaultForm is created and configured.");
		
		addLayoutDetails(RulesCommonVariables.FocusLayout, RulesCommonVariables.FocusLayout, RulesCommonVariables.FocusLayout, true, true, true);
		selectAndConfigureAnyBuildingBlock(EntityCommonVariables.LAYOUTBBPARENT, RulesCommonVariables.FocusLayout);
		dragAndDrop(PanelType.BREADCRUMBS, 40,40);
		dragAndDrop(PanelType.FORM, 20,20);
		addPanelProperties(RulesCommonVariables.createForm,"Full", RulesCommonVariables.createForm);
		layoutEditorSaveAndClose(true);
		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("layout is added and configured."); 
		saveAndCloseEntity();      
	    cwsdoc.publishToOrganization(RulesCommonVariables.ENTITY10319);
	    Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("Entity is published succesfully.");
	    
	    /*
	     * provide Solution Security in adminpage 
	     */
	    provideSolutionSecurityInAdminPage(RulesCommonVariables.RULEBBADVANCEDEXPRESSIONPROJECT);
	    
	    /*
	     * Run Time
	     */
	    ApplicationFactory.launchProcessExperienceURL();
        PECommonFunctions.selectHomePageLayout("Home Page");
        Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("Creating an instance of the entity");       
        PECommonFunctions.selectEntityToCreateInstance(RulesCommonVariables.ENTITY10319);
        Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("Created and opened the Instance.");
        PECommonFunctions.clickCreateButton(CreateEntity.CREATE_AND_OPEN,RulesCommonVariables.ENTITY10319);
        ReusableMethods.clickElement(PEFormElements.selectRadioBtnInForm("ABool","true",PEFormTypes.DEFAULT));
        PECommonFunctions.saveChangesInPE(PEFormElements.textSetInTextFields(RulesCommonVariables.ATextProperty));
        ReusableMethods.customSleep(3);
        AssertionUtilities.assertTextComparison(RulesCommonVariables.ATextProperty, "", false, "moving the mouse test case passed");
        Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("moving the mouse is set as Expected");
	}
	
	/*
	 *   [PP.10320] Verify the rule behavior on creating a rule with "A property changes" which starts the process "When the condition changes to true" "on lost focus of property"
	 */
	@Test(timeOut = 20 * 60 * 1000)
	public void pp10320VerifywhetherBooleanischanged() throws InterruptedException {
		
 		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("Starting Execution of test case pp10320VerifyBoolischanged. To Verify When a property in a condition is changed on lost focus of form");
  		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("PP.102320---Creating Rule to start specified process when property in a condition is changed on lost focus of form");
  		createAndPublishBPM("SampleBPM",RulesCommonVariables.RULEBBADVANCEDEXPRESSIONWS, RulesCommonVariables.RULEBBADVANCEDEXPRESSIONPROJECT,RulesCommonVariables.RULESFOLDERNAME9);
 		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("SampleBPM has been created and published successfully");
 		
 		createOrGoToEntity(RulesCommonVariables.ENTITY10320,RulesCommonVariables.RULEBBADVANCEDEXPRESSIONWS, RulesCommonVariables.RULEBBADVANCEDEXPRESSIONPROJECT,RulesCommonVariables.RULESFOLDERNAME9);
 		createProperty(RulesCommonVariables.ATextProperty,RulesCommonVariables.ATextProperty, PropertyType.TYPE_TEXT);
 		createProperty(RulesCommonVariables.ABoolProperty, RulesCommonVariables.ABoolProperty, PropertyType.TYPE_BOOL);
 		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("Properties are added");
 		
 		addListDetails(RulesCommonVariables.list10320, RulesCommonVariables.list10320, RulesCommonVariables.LISTCATEGORY, null, false, "200",null);
 		selectAndConfigureAnyBuildingBlock(EntityCommonVariables.LISTBBPARENT, RulesCommonVariables.list10320);
 		addPropertiesToListWithSettings(RulesCommonVariables.ATextProperty, false,null, null, null);
 		addPropertiesToListWithSettings(RulesCommonVariables.ABoolProperty, false,null, null, null);
 		saveAndCloseListEditor(true);
 		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("List is added and configured.");
 		
 		String[] condition = { "ABool" };
 		addRule("BPMRule","BPMRule");
 		selectAndConfigureAnyBuildingBlock(EntityCommonVariables.RULEBBPARENT, "BPMRule");
 		setWhenWithCondition(ruleTrigger.TYPE_PROPERTYCHANGE, null, RulesCommonVariables.IFCONDITIONONE, condition, RulesCommonVariables.SELECTCONDITIONEQUAL10);
 		startProcessRule(Then.STARTPROCESS, "SampleBPM", "AText");
 		saveRuleEditor();
 		entityNative.save();		
 		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("Rules is added and configured.");
 		
 		addFormDetails(RulesCommonVariables.createForm, RulesCommonVariables.createForm, RulesCommonVariables.createForm);
 		selectAndConfigureAnyBuildingBlock(EntityCommonVariables.FORMBBPARENT, RulesCommonVariables.createForm);
 		formEditorView.dragAndDropProperty(RulesCommonVariables.ATextProperty, 20, 15);
 		formEditorView.dragAndDropProperty(RulesCommonVariables.ABoolProperty, 40,15);
 		saveAndCloseFormEditor();
 		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("CreateForm is created and configured.");
 		
 		addFormDetails(RulesCommonVariables.defaultForm, RulesCommonVariables.defaultForm, RulesCommonVariables.defaultForm);
 		selectAndConfigureAnyBuildingBlock(EntityCommonVariables.FORMBBPARENT, RulesCommonVariables.defaultForm);
 		formEditorView.dragAndDropProperty(RulesCommonVariables.ATextProperty, 20, 15);
 		formEditorView.dragAndDropProperty(RulesCommonVariables.ABoolProperty, 40,15);
 		saveAndCloseFormEditor();
 		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("DefaultForm is created and configured.");
 		
 		addLayoutDetails(RulesCommonVariables.FocusLayout, RulesCommonVariables.FocusLayout, RulesCommonVariables.FocusLayout, true, true, true);
 		selectAndConfigureAnyBuildingBlock(EntityCommonVariables.LAYOUTBBPARENT, RulesCommonVariables.FocusLayout);
 		dragAndDrop(PanelType.BREADCRUMBS, 40,40);
 		dragAndDrop(PanelType.FORM, 20,20);
 		addPanelProperties(RulesCommonVariables.createForm,"Full", RulesCommonVariables.createForm);
 		layoutEditorSaveAndClose(true);
 		Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("layout is added and configured."); 
 		saveAndCloseEntity();      
 	    cwsdoc.publishToOrganization(RulesCommonVariables.ENTITY10320);
 	    Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("Entity is published succesfully.");
 	    
 	    /*
 	     * provide Solution Security in adminpage 
 	     */
 	    provideSolutionSecurityInAdminPage(RulesCommonVariables.RULEBBADVANCEDEXPRESSIONPROJECT);
	    
	    /*
	     * Run Time
	     */
	    ApplicationFactory.launchProcessExperienceURL();
        PECommonFunctions.selectHomePageLayout("Home Page");
        Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("Creating an instance of the entity");       
        PECommonFunctions.selectEntityToCreateInstance(RulesCommonVariables.ENTITY10320);
        Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("Created and opened the Instance.");
        PECommonFunctions.clickCreateButton(CreateEntity.CREATE_AND_OPEN,RulesCommonVariables.ENTITY10320);
        ReusableMethods.clickElement(PEFormElements.selectRadioBtnInForm("ABool","true",PEFormTypes.DEFAULT));
        PECommonFunctions.saveChangesInPE(PEFormElements.textSetInTextFields(RulesCommonVariables.ATextProperty));
        System.out.println("====>"+PEForm.getTextFromTextFieldsInForm(RulesCommonVariables.ATextProperty, PEFormTypes.DEFAULT));
        AssertionUtilities.assertTextComparison(RulesCommonVariables.ATextProperty, "", false, "moving the mouse test case passed");
        Logger.getLogger(RueBB_RuleBehaviour.class.getName()).info("AText value is set immediately as Expected");
	}

}