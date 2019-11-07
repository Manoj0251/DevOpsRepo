package com.opentext.rules;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.opentext.accelerators.ApplicationFactory;
import com.opentext.accelerators.AssertionUtilities;
import com.opentext.accelerators.ReusableMethods;
import com.opentext.accelerators.WaitUtilities;
import com.opentext.accelerators.WaitUtilities.WaitDuration;
import com.opentext.commonutils.EntityCommonUtils;
import com.opentext.commonvariables.EntityCommonVariables;
import com.opentext.commonvariables.ProcessExperienceCommonVariables;
import com.opentext.commonvariables.RulesCommonVariables;
import com.opentext.entity.buildingblock.detailview.LayoutPropertiesView.PanelType;
import com.opentext.entity.buildingblock.detailview.PropertyPropertiesView;
import com.opentext.entity.buildingblock.detailview.PropertyPropertiesView.PropertyType;
import com.opentext.entity.buildingblock.editorview.FormEditorView;
import com.opentext.entity.buildingblock.editorview.RuleEditorView;
import com.opentext.entity.buildingblock.editorview.RuleEditorView.RuleAdvancedGroup;
import com.opentext.entity.buildingblock.editorview.RuleEditorView.Then;
import com.opentext.entity.buildingblock.editorview.RuleEditorView.ruleTrigger;
import com.opentext.objectrepository.processexperience.PECommonFunctionElements;
import com.opentext.objectrepository.processexperience.PECommonFunctionElements.ActionTypes;
import com.opentext.objectrepository.processexperience.PEFormElements;
import com.opentext.objectrepository.processexperience.PEFormElements.PEFormTypes;
import com.opentext.objectrepository.processexperience.PEResultsElements;
import com.opentext.objectrepository.processexperience.PEResultsElements.PEResultMenuButtons;
import com.opentext.workflow.entity.EntityNative;
import com.opentext.workflow.platform.CWSDocument;
import com.opentext.workflow.platform.CommonFunctions;
import com.opentext.workflow.platform.OrganizeWorkspaces;
import com.opentext.workflow.processexperience.PECommonFunctions;
import com.opentext.workflow.processexperience.PECommonFunctions.CreateEntity;
import com.opentext.workflow.processexperience.PEForm;
import com.opentext.workflow.processexperience.PELists;
import com.opentext.workflow.processexperience.PEResults;
import com.opentext.workflow.processexperience.ProcessExperienceAdmin;

public class RuleBB_Actions extends EntityCommonUtils{
	public static boolean sessionFlag = false;
	
	@BeforeMethod(timeOut = 10 * 60 * 1000)
	public void preRequisite() throws InterruptedException {
		Logger.getLogger(RuleBB_Actions.class.getName()).info("Execution of Actions preRequisite");
		if(sessionFlag==false) {
			ApplicationFactory.launchProcessPlatform();
			wsd = new OrganizeWorkspaces();
			cwsdoc = new CWSDocument();
			entityNative = new EntityNative();
			formEditorView = new FormEditorView();
			propertiesView = new PropertyPropertiesView();
			ruleEditorView =  new RuleEditorView();
			CommonFunctions.startFromCusp(OrganizeWorkspaces.class);
			wsd.openOrCreateNewWorkspace(RulesCommonVariables.RULEBBACTIONWS, RulesCommonVariables.RULEBBACTIONPROJECT,RulesCommonVariables.PKGOWNER);
			entityNative.createEntityInCWS(RulesCommonVariables.RULEBBACTIONPROJECT, RulesCommonVariables.RULESFOLDERNAME1,"SampleEntity", true);
			cwsdoc.publishToOrganization("SampleEntity");
			ApplicationFactory.launchProcessAdminURL();
			processExperienceAdmin = new ProcessExperienceAdmin();
			processExperienceAdmin.selectProject(RulesCommonVariables.PKGOWNER+RulesCommonVariables.RULEBBACTIONPROJECT,ProcessExperienceCommonVariables.ORGANIZATION);
			processExperienceAdmin.configureSolution(ProcessExperienceCommonVariables.CONFIGSOLUTION,ProcessExperienceCommonVariables.SOLUTIONTYPE, ProcessExperienceCommonVariables.ROLES);
			sessionFlag=true;
		}
	}
		
	@Test(timeOut = 30 * 60 * 1000)
	public void pp8253FormOpenOnActionClick() throws InterruptedException 	{
		Logger.getLogger(RuleBB_Actions.class.getName()).info("Starting Execution of test case pp8253FormOpenOnActionClick ");
		
		ApplicationFactory.launchProcessPlatformURL();
		CommonFunctions.startFromCusp(OrganizeWorkspaces.class);
		wsd.openOrCreateNewWorkspace(RulesCommonVariables.RULEBBACTIONWS, RulesCommonVariables.RULEBBACTIONPROJECT,RulesCommonVariables.PKGOWNER);
			
		entityNative.createEntityInCWS(RulesCommonVariables.RULEBBACTIONPROJECT, RulesCommonVariables.RULESFOLDERNAME1,RulesCommonVariables.ENTITY8253, true);
		
		entityNative.openExistingDocument(EntityNative.class, RulesCommonVariables.ENTITY8253);		
		createProperty("Integer", "Integer", PropertyType.TYPE_INTEGER);
		createProperty("text", "text", PropertyType.TYPE_TEXT);
		
		addFormDetails("Create", "Create", "Create");
		selectAndConfigureAnyBuildingBlock("Forms", "Create");
		formEditorView.dragAndDropProperty("Integer", 20, 20);
		formEditorView.dragAndDropProperty("text", 40,20);
		saveAndCloseFormEditor();
		
		addFormDetails("Form1", "Form1", "Form1");
		selectAndConfigureAnyBuildingBlock("Forms", "Form1");
		formEditorView.dragAndDropProperty("Integer", 20, 20);
		formEditorView.dragAndDropProperty("text", 40,20);
		saveAndCloseFormEditor();

		addListDetails("openformlist41", "openformlist41", RulesCommonVariables.LISTCATEGORY, null, false, "200", null);
		selectAndConfigureAnyBuildingBlock(EntityCommonVariables.LISTBBPARENT, "openformlist41");
		addPropertiesToListWithSettings("Integer", false, null, null, null);
		addPropertiesToListWithSettings("text", false, null, null, null);
		saveAndCloseListEditor(true);
		
		String[][][] condition = { { { "equal to", "abc", "" } } };
		String[] text = {"text"};
				
		createTriggerActionRuleWithConditions("OpenFormOnActionClick", "A user triggers an action button", RulesCommonVariables.IFCONDITIONONE, text, condition,Then.SET, "Integer", "123");
		selectAdvancedConfigurationsTriggerRuleAction("OpenFormOnActionClick", RuleAdvancedGroup.BeforeActionLabelLabel,null,null,null,"Open a form","Form1",null);
		
		entityNative.save();
		//saveAndCloseEntity();
		//openEntityInCWSFromFolder(RulesCommonVariables.RULESFOLDERNAME1,RulesCommonVariables.ENTITY8253);
		
		addActionBar("RulesActions");
		selectAndConfigureAnyBuildingBlock(EntityCommonVariables.ACTIONBARBBPARENT, "RulesActions");
		actionButton("Open", "Identity");
		actionButton("Delete", "Identity");
		actionButton("Print", "Identity");
		actionButton("OpenFormOnActionClick", "Rules");
		saveActionEditor();
		
		addLayoutDetails("Layout2064", "Layout2064", "Layout2064", false, true, false);
		selectAndConfigureAnyBuildingBlock("Layouts", "Layout2064");
		dragAndDrop(PanelType.FORM, 0,20);
		addPanelProperties("Create","Full", "Create");
		dragAndDrop(PanelType.ACTIONS, 20, 40);
		addPanelPropertiesWithActionBar("RuleActions", "Full", "RulesActions");
		layoutEditorSaveAndClose(true);
		
		saveAndCloseEntity();
		cwsdoc.publishToOrganization(RulesCommonVariables.ENTITY8253);
				
		ApplicationFactory.launchProcessExperienceURL();
		PECommonFunctions.selectHomePageLayout("Home Page");
		PECommonFunctions.selectEntityToCreateInstance(RulesCommonVariables.ENTITY8253);
		PECommonFunctions.clickCreateButton(CreateEntity.CREATE_AND_OPEN, RulesCommonVariables.ENTITY8253);
		
		PEForm.setInputTextInTextFields("text", "abc",PEFormTypes.DEFAULT);
		PEForm.setInputTextInTextFields("Integer", "123",PEFormTypes.DEFAULT);
		PECommonFunctions.saveForm("Default");
		PECommonFunctions.saveChangesInPE(PEFormElements.textSetInTextFields("text", PEFormTypes.DEFAULT));
		
		AssertionUtilities.assertElementPresence(PEResults.ruleActionBtns("OpenFormOnActionClick"), true, "OpenFormOnActionClick button is not displayed");
		ReusableMethods.clickElement(PEResults.ruleActionBtns("OpenFormOnActionClick"));
		WaitUtilities.wdVerifyWaitVisibility(By.xpath(PEFormElements.setuserEventFormName("OpenFormOnActionClick")),WaitDuration.MINWAIT);
				
	} 

	@Test(timeOut = 30 * 60 * 1000,dependsOnMethods="pp8253FormOpenOnActionClick")
	public void pp8253PresenceofRuleActionButtonInPE() throws InterruptedException 	{
		Logger.getLogger(RuleBB_Actions.class.getName()).info("Starting Execution of test case pp8253PresenceofRuleActionButtonInPE ");
		
		ApplicationFactory.launchProcessPlatformURL();
		CommonFunctions.startFromCusp(OrganizeWorkspaces.class);
		wsd.openOrCreateNewWorkspace(RulesCommonVariables.RULEBBACTIONWS, RulesCommonVariables.RULEBBACTIONPROJECT,RulesCommonVariables.PKGOWNER);
			
		entityNative.createEntityInCWS(RulesCommonVariables.RULEBBACTIONPROJECT, RulesCommonVariables.RULESFOLDERNAME1,RulesCommonVariables.ENTITY8253, true);
		entityNative.openExistingDocument(EntityNative.class, RulesCommonVariables.ENTITY8253);		
				
		addFormDetails("Default", "Default", "Default");
		selectAndConfigureAnyBuildingBlock("Forms", "Default");
		formEditorView.dragAndDropProperty("Integer", 20, 20);
		formEditorView.dragAndDropProperty("text", 40, 20);
		saveAndCloseFormEditor();
				
		saveAndCloseEntity();
		cwsdoc.publishToOrganization(RulesCommonVariables.ENTITY8253);
		
		ApplicationFactory.launchProcessExperienceURL();
		PECommonFunctions.selectHomePageLayout("Home Page");
		PECommonFunctions.selectEntityToCreateInstance(RulesCommonVariables.ENTITY8253);
		WaitUtilities.wdWaitForPresenceOfElementLocated(PEFormElements.textSetInTextFields("Integer",PEFormTypes.CREATE), WaitDuration.MINWAIT);
		PECommonFunctions.clickCreateButton(CreateEntity.CREATE_AND_OPEN, RulesCommonVariables.ENTITY8253);
		
		PEForm.setInputTextInTextFields("text", "abc",PEFormTypes.DEFAULT);
		PECommonFunctions.saveForm("Default");
		PECommonFunctions.saveChangesInPE(PEFormElements.textSetInTextFields("text", PEFormTypes.DEFAULT));
		
		if(ReusableMethods.isElementEnabled(PEResults.ruleActionBtns("OpenFormOnActionClick"))) {
			ReusableMethods.clickElement(PEFormElements.ruleActionBtns("OpenFormOnActionClick"));
			ReusableMethods.clickElement(PEFormElements.ruleActionBtns("OK"));
			Logger.getLogger(RuleBB_Actions.class.getName()).info("\n Rule Action OpenFormOnActionClick button is available and clickable and form dialog box is opened");
		}else {
			AssertionUtilities.assertBoolean(false, true, "Rule Action Button is not clickable");
		}
			
		WaitUtilities.wdWaitTextInElementValue(PEFormElements.textSetInTextFields("Integer",PEFormTypes.DEFAULT),"123");
		PECommonFunctions.saveChangesInPE(PEFormElements.textSetInTextFields("Integer", PEFormTypes.DEFAULT));
		
		AssertionUtilities.assertTextComparison(PEForm.getTextFromTextFieldsInForm("Integer",PEFormTypes.DEFAULT), "123", true, "Expected text did not set in Text field when OpenFormOnActionClick Rule btn is clicked");
		PEForm.clearText(PEFormElements.textSetInTextFields("text", PEFormTypes.DEFAULT));
		PEForm.setInputTextInTextFields("text", "12",PEFormTypes.DEFAULT);
		PEForm.clearText(PEFormElements.textSetInTextFields("Integer", PEFormTypes.DEFAULT));
		PECommonFunctions.saveForm("Default");
		PECommonFunctions.saveChangesInPE(PEFormElements.textSetInTextFields("text", PEFormTypes.DEFAULT));
		
		ReusableMethods.actionClick(PEFormElements.btnRuleActionsText);
		PECommonFunctions.saveForm("Default");				
		AssertionUtilities.assertElementPresence(PEResults.ruleActionBtns("OpenFormOnActionClick"), false, "OpenFormOnActionClick button is not displayed");
	} 
	
	@Test(timeOut = 30 * 60 * 1000)
	public void pp3725OnActionPerformResultsListRefresh() throws InterruptedException {
		Logger.getLogger(RuleBB_Actions.class.getName()).info("Starting Execution of test case pp3725OnActionPerformResultsListRefresh ");
		
		ApplicationFactory.launchProcessPlatformURL();
		CommonFunctions.startFromCusp(OrganizeWorkspaces.class);
		wsd.openOrCreateNewWorkspace(RulesCommonVariables.RULEBBACTIONWS, RulesCommonVariables.RULEBBACTIONPROJECT,RulesCommonVariables.PKGOWNER);
		
		entityNative.createEntityInCWS(RulesCommonVariables.RULEBBACTIONPROJECT, RulesCommonVariables.RULESFOLDERNAME1,RulesCommonVariables.ENTITY3725, true); 
		entityNative.openExistingDocument(EntityNative.class, RulesCommonVariables.ENTITY3725 );

		createProperty("Integer", "Integer", PropertyType.TYPE_INTEGER);
		createProperty("Text", "Text", PropertyType.TYPE_TEXT);

		addFormDetails("Create", "Create", "Create");
		selectAndConfigureAnyBuildingBlock("Forms", "Create");
		String integer = formEditorView.dragAndDropProperty("Integer", 20, 20);
		String Text = formEditorView.dragAndDropProperty("Text", 30, 30);
		String[] fields = { integer, Text };
		formEditorView.alignComponents(fields);
		saveAndCloseFormEditor();

		addListDetails("list3725", "list3725", RulesCommonVariables.LISTCATEGORY, null, false, "200", null);
		selectAndConfigureAnyBuildingBlock(EntityCommonVariables.LISTBBPARENT, "list3725");
		addPropertiesToListWithSettings("Integer", false, null, null, null);
		addPropertiesToListWithSettings("Text", false, null, null, null);
		saveAndCloseListEditor(true);
		
		createTriggerActionRuleWithoutConditions("TriggerRule", "A user triggers an action button", Then.SET, "Text", "\"testing action button\"");
		
		//saveAndCloseEntity();
		//openEntityInCWSFromFolder(RulesCommonVariables.RULESFOLDERNAME1,RulesCommonVariables.ENTITY3725);
		
		addActionBar("RuleActions"); 
		selectAndConfigureAnyBuildingBlock(EntityCommonVariables.ACTIONBARBBPARENT, "RuleActions");
		actionButton("Open", "Identity");
		actionButton("Delete", "Identity");
		actionButton("Print", "Identity");
		actionButton("TriggerRule", "Rules");
		saveActionEditor();		
		saveAndCloseEntity();
		cwsdoc.publishToOrganization(RulesCommonVariables.ENTITY3725);

		ApplicationFactory.launchProcessExperienceURL();
		PECommonFunctions.selectHomePageLayout("Home Page");
		PECommonFunctions.selectEntityToCreateInstance(RulesCommonVariables.ENTITY3725);
		PEForm.setInputTextInTextFields("Integer", "1",PEFormTypes.CREATE);
		PECommonFunctions.clickCreateButton(PECommonFunctions.CreateEntity.CREATE, RulesCommonVariables.ENTITY3725);	
		PELists.selectWorkList("list3725",RulesCommonVariables.LISTCATEGORY);
		PEResults.clickResultsPanelInstanceBtn("Integer", "1", PEResultMenuButtons.TRIGGERRULE);
		WaitUtilities.waitForElementToDisplay(PEResults.ruleActionBtns("TriggerRule"), WaitDuration.MINWAIT);
		AssertionUtilities.assertElementPresence(PEResults.ruleActionBtns("TriggerRule"), true, "Trigger Rule action button is not present");
		AssertionUtilities.softAssertElementPresence(PEResultsElements.resultInstances("Text", "testing action button"),true,"Rule is not triggered");	
		Logger.getLogger(RuleBB_Actions.class.getName()).info("As expected Rule is triggered and Action button is displayed ");
	}
	
	@Test(timeOut = 30 * 60 * 1000) //issue raised - BOP-105503
	public void pp8261errorPresenceOnInstanceIntialization() throws InterruptedException {
		Logger.getLogger(RuleBB_Actions.class.getName()).info("Starting Execution of test case pp8261errorPresenceOnInstanceIntialization ");
		
		ApplicationFactory.launchProcessPlatformURL();
		CommonFunctions.startFromCusp(OrganizeWorkspaces.class);
		wsd.openOrCreateNewWorkspace(RulesCommonVariables.RULEBBACTIONWS, RulesCommonVariables.RULEBBACTIONPROJECT,RulesCommonVariables.PKGOWNER);
		
		entityNative.createEntityInCWS(RulesCommonVariables.RULEBBACTIONPROJECT, RulesCommonVariables.RULESFOLDERNAME1,RulesCommonVariables.ENTITY8261, true);
		entityNative.openExistingDocument(EntityNative.class, RulesCommonVariables.ENTITY8261);

		createProperty("Integer", "Integer", PropertyType.TYPE_INTEGER);	
		createProperty("Text", "Text", PropertyType.TYPE_TEXT);
		entityNative.selectBuildingBlock("Properties", "Integer");
		propertiesView.setDefaultValue("121");
		addFormDetails("Create", "Create", "Create");
		selectAndConfigureAnyBuildingBlock("Forms", "Create");
		String integer = formEditorView.dragAndDropProperty("Integer", 20, 20);
		String Text = formEditorView.dragAndDropProperty("Text", 30, 30);
		String[] fields = { integer, Text };
		formEditorView.alignComponents(fields);
		saveAndCloseFormEditor();
		
		addListDetails("errorpresencelist", "errorpresencelist", RulesCommonVariables.LISTCATEGORY, null, false, "200", null);
		selectAndConfigureAnyBuildingBlock(EntityCommonVariables.LISTBBPARENT, "errorpresencelist");
		addPropertiesToListWithSettings("Integer", false, null, null, null);
		addPropertiesToListWithSettings("Text", false, null, null, null);
		saveAndCloseListEditor(true);

		String[] condition1= {"Integer"};
		createRuleWithConditions("IntegerRule", ruleTrigger.TYPE_INSTANCEINITIALIZED, null, RulesCommonVariables.IFCONDITIONONE, condition1, RulesCommonVariables.SELECTCONDITIONEQUAL1,Then.ERROR, "Integer Error", "");

	    entityNative.save();
	    saveAndCloseEntity();
		
		cwsdoc.publishToOrganization(RulesCommonVariables.ENTITY8261);

		ApplicationFactory.launchProcessExperienceURL();
		PECommonFunctions.selectHomePageLayout("Home Page");
		PECommonFunctions.selectEntityToCreateInstance(RulesCommonVariables.ENTITY8261);

		// Related to BOP- 107731 form is doesn't load when a error is available if Item is Initialized rule is used 
		//ReusableMethods.assertBoolean(PECommonFunctions.verifyErrorOrWarningMessages(ActionTypes.ERROR, "Integer Error"), true, "Error message is not displayed ");
		
		AssertionUtilities.assertBoolean(PECommonFunctions.isElementDisplayed(PECommonFunctionElements.errorORWarningAlertMessage("Integer Error")),true, "Error message is displayed, Due to Item Initialized with Form is not loading as per BOP -102026");
		AssertionUtilities.assertBoolean(PECommonFunctions.isElementDisplayed(PEFormElements.input("integer")), false, "Form is should not load as per BOP -102026 ");
		Logger.getLogger(RuleBB_Actions.class.getName()).info("Rule is triggered and expected error message is not displayed as Form doesn't load when rule with Item initialized is used");
	}
	
	@Test(timeOut = 30 * 60 * 1000)
	public void PP6021setPropertyOnInstanceIntialization() throws InterruptedException {
		Logger.getLogger(RuleBB_Actions.class.getName()).info("Starting Execution of test case PP6021setPropertyOnInstanceIntialization ");
		
		ApplicationFactory.launchProcessPlatformURL();
		CommonFunctions.startFromCusp(OrganizeWorkspaces.class);
		wsd.openOrCreateNewWorkspace(RulesCommonVariables.RULEBBACTIONWS, RulesCommonVariables.RULEBBACTIONPROJECT,RulesCommonVariables.PKGOWNER);
		
		entityNative.createEntityInCWS(RulesCommonVariables.RULEBBACTIONPROJECT, RulesCommonVariables.RULESFOLDERNAME1,RulesCommonVariables.ENTITY6021, true);
		entityNative.openExistingDocument(EntityNative.class, RulesCommonVariables.ENTITY6021);

		createProperty("Integer", "Integer", PropertyType.TYPE_INTEGER);
		createProperty("Text", "Text", PropertyType.TYPE_TEXT);
		entityNative.selectBuildingBlock("Properties", "Integer");
		propertiesView.setDefaultValue("121");
		addFormDetails("Create", "Create", "Create");
		selectAndConfigureAnyBuildingBlock("Forms", "Create");
		String integer = formEditorView.dragAndDropProperty("Integer", 20, 20);
		String Text = formEditorView.dragAndDropProperty("Text", 30, 30);
		String[] fields = { integer, Text };
		formEditorView.alignComponents(fields);
		saveAndCloseFormEditor();
		
		addListDetails("PP6021REList", "PP6021REList", RulesCommonVariables.LISTCATEGORY, null, false, "200", null);
		selectAndConfigureAnyBuildingBlock(EntityCommonVariables.LISTBBPARENT, "PP6021REList");
		addPropertiesToListWithSettings("Integer", false, null, null, null);
		addPropertiesToListWithSettings("Text", false, null, null, null);
		saveAndCloseListEditor(true);

		String[] condition1= {"Integer"};
		createRuleWithConditions("IntegerRule", ruleTrigger.TYPE_INSTANCEINITIALIZED, null, RulesCommonVariables.IFCONDITIONONE, condition1, RulesCommonVariables.SELECTCONDITIONEQUAL1,Then.SET, "Text", "\"Good\"");
		

	    entityNative.save();
		saveAndCloseEntity();
		cwsdoc.publishToOrganization(RulesCommonVariables.ENTITY6021);
		
		ApplicationFactory.launchProcessExperienceURL();
		PECommonFunctions.selectHomePageLayout("Home Page");
		PECommonFunctions.selectEntityToCreateInstance(RulesCommonVariables.ENTITY6021);
		
		AssertionUtilities.assertTextComparison(PEForm.getTextFromTextFieldsInForm("Text",PEFormTypes.CREATE), "Good", true, "Expected value is not is text field on instance initialization");
		Logger.getLogger(RuleBB_Actions.class.getName()).info("Rule is triggered and expected value is set in text field");
	}
	
	@Test(timeOut = 30 * 60 * 1000) 
	public void pp8274And8275ActionPerformOnErrorPresence() throws InterruptedException {
		Logger.getLogger(RuleBB_Actions.class.getName()).info("Starting Execution of test case pp8274And8275ActionPerformOnErrorPresence ");
		
		ApplicationFactory.launchProcessPlatformURL();
		CommonFunctions.startFromCusp(OrganizeWorkspaces.class);
		wsd.openOrCreateNewWorkspace(RulesCommonVariables.RULEBBACTIONWS, RulesCommonVariables.RULEBBACTIONPROJECT,RulesCommonVariables.PKGOWNER);
		
		entityNative.createEntityInCWS(RulesCommonVariables.RULEBBACTIONPROJECT, RulesCommonVariables.RULESFOLDERNAME1,RulesCommonVariables.ENTITY8274, true);
		entityNative.openExistingDocument(EntityNative.class, RulesCommonVariables.ENTITY8274);

		createProperty("Integer", "Integer", PropertyType.TYPE_INTEGER);
		createProperty("Text", "Text", PropertyType.TYPE_TEXT);
		createProperty("Decimal", "Decimal", PropertyType.TYPE_DECIMAL);

		addFormDetails("Create", "Create", "Create");
		selectAndConfigureAnyBuildingBlock("Forms", "Create");
		String integer = formEditorView.dragAndDropProperty("Integer", 20, 20);
		String Decimal= formEditorView.dragAndDropProperty("Decimal",10, 10);
		formEditorView.setPropertyDisplayOption("Required");
		String Text = formEditorView.dragAndDropProperty("Text", 30, 30);
		String[] fields = { integer, Text,Decimal };
		formEditorView.alignComponents(fields);
		saveAndCloseFormEditor();
		
		addFormDetails("Default", "Default", "Default");
		selectAndConfigureAnyBuildingBlock("Forms", "Default");
		String integer1 = formEditorView.dragAndDropProperty("Integer", 20, 20);
		String Decimal1= formEditorView.dragAndDropProperty("Decimal",10, 10);
		formEditorView.setPropertyDisplayOption("Required");
		String Text1 = formEditorView.dragAndDropProperty("Text", 30, 30);
		String[] fields1 = { integer1, Text1,Decimal1 };
		formEditorView.alignComponents(fields1);
		saveAndCloseFormEditor();

		
		addListDetails("formlist8274_75", "formlist8274_75", RulesCommonVariables.LISTCATEGORY, null, false, "200", null);
		selectAndConfigureAnyBuildingBlock(EntityCommonVariables.LISTBBPARENT, "formlist8274_75");
		addPropertiesToListWithSettings("Integer", false, null, null, null);
		addPropertiesToListWithSettings("Text", false, null, null, null);
		saveAndCloseListEditor(true);
		
		String[][][] condition = { { { "equal to", "abc", "" } } };
		String[] text = {"Text"};
		
		createTriggerActionRuleWithConditions("AllowActionsOnErrorRule", "A user triggers an action button", RulesCommonVariables.IFCONDITIONONE, text, condition, Then.SET, "Integer", "123");
		selectAdvancedConfigurationsTriggerRuleAction("AllowActionsOnErrorRule",RuleAdvancedGroup.EnableOnErrorsLabel , null, null, null, null, null, null);
		entityNative.save();
				
		addActionBar("RulesActions");
		selectAndConfigureAnyBuildingBlock(EntityCommonVariables.ACTIONBARBBPARENT, "RulesActions");
		actionButton("Open", "Identity");
		actionButton("Delete", "Identity");
		actionButton("Print", "Identity");
		actionButton("AllowActionsOnErrorRule", "Rules");
		saveActionEditor();
				
		EntityCommonUtils.addLayoutDetails("Layout", "Layout", "Layout", false, true, false);
		EntityCommonUtils.selectAndConfigureAnyBuildingBlock("Layouts", "Layout");
		dragAndDrop(PanelType.FORM, 0, 20);//20
		addPanelProperties("Form", "Full", "Create");
	    dragAndDrop(PanelType.ACTIONS, 2, 20);//30
	    addPanelPropertiesWithActionBar("RuleActions", "Full", "RulesActions");
		layoutEditorSaveAndClose(true);
		
		saveAndCloseEntity();
		cwsdoc.publishToOrganization(RulesCommonVariables.ENTITY8274);

		ApplicationFactory.launchProcessExperienceURL();
		PECommonFunctions.selectHomePageLayout("Home Page");	
		PECommonFunctions.selectEntityToCreateInstance(RulesCommonVariables.ENTITY8274);
		PEForm.setInputTextInTextFields("Decimal", "21", PEFormTypes.CREATE);
		PEForm.setInputTextInTextFields("Text", "abc", PEFormTypes.CREATE);
		PECommonFunctions.clickCreateButton(CreateEntity.CREATE_AND_OPEN, RulesCommonVariables.ENTITY8274);
		WaitUtilities.wdWaitVisibility(PEFormElements.textSetInTextFields("Decimal", PEFormTypes.DEFAULT),WaitDuration.MEDWAIT);
		PEForm.clearText(PEFormElements.textSetInTextFields("Decimal", PEFormTypes.DEFAULT));
		PECommonFunctions.saveChangesInPE(PEFormElements.textSetInTextFields("Decimal", PEFormTypes.DEFAULT));
		AssertionUtilities.assertBoolean(PECommonFunctions.verifyErrorOrWarningMessages(ActionTypes.ERROR, "Enter a Decimal."), true, "Decimal is required: Error message is not displayed");
		AssertionUtilities.assertElementPresence(PEFormElements.ruleActionBtns("AllowActionsOnErrorRule"), true, "AllowActionsOnErrorRule Rule action button not present");
		ReusableMethods.clickElement(PEFormElements.ruleActionBtns("AllowActionsOnErrorRule"));
		WaitUtilities.wdWaitTextInElementValue(PEFormElements.textSetInTextFields("Integer",PEFormTypes.DEFAULT),"123",WaitDuration.MEDWAIT);
		AssertionUtilities.assertTextComparison(PEForm.getTextFromTextFieldsInForm("Integer",PEFormTypes.DEFAULT), "123", true, "Expected text did not set in Text field when AllowActionsOnErrorRule Rule btn is clicked");
		Logger.getLogger(RuleBB_Actions.class.getName()).info("As expected Integer Rule action btn is displayed and also expected text is set in Text field on error presence");
	}
}
