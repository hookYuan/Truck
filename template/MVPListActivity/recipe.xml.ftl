<?xml version="1.0"?>
<recipe>
    <#include "recipe_manifest.xml.ftl" />

	<instantiate from="root/src/app_package/SampleActivity.java.ftl"
     	to="${escapeXmlAttribute(srcOut)}/${activityClass}.java" />
		
	<instantiate from="root/res/layout/activity_sample.xml.ftl"
		to="${escapeXmlAttribute(resOut)}/layout/${activityLayoutName}.xml" />
	
	<instantiate from="root/res/layout/activity_sample_item.xml.ftl"
		to="${escapeXmlAttribute(resOut)}/layout/${activityLayoutName}_item.xml" />
		
	<instantiate from="root/src/app_package/NonePresenter.java.ftl"
     	to="${escapeXmlAttribute(manifestOut)}/java/${slashedPackageName(applicationPackage)}/view/${modular}/presenter/${activityName}Presenter.java" />

	<instantiate from="root/src/app_package/RLVAdapter.java.ftl"
     	to="${escapeXmlAttribute(manifestOut)}/java/${slashedPackageName(applicationPackage)}/view/${modular}/adapter/${activityName}Adapter.java" />
		
	<open file="${escapeXmlAttribute(resOut)}/layout/${activityLayoutName}_item.xml"/>        

 	<open file="${escapeXmlAttribute(srcOut)}/${activityClass}.java" />
</recipe>
