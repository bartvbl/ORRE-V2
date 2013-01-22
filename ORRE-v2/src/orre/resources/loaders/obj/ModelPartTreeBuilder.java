package orre.resources.loaders.obj;

import java.util.ArrayList;
import java.util.List;

import nu.xom.Element;
import nu.xom.Elements;

import orre.resources.data.BlueprintModel;

public class ModelPartTreeBuilder {
	private static final String PHYSICAL_PART = "part";
	private static final String SHADER_PART = "shaderPart";

	public static List<StoredModelPart> generatePartTree(BlueprintModel model, Element rootElement) {
		Element partStructureElement = rootElement.getFirstChildElement("partStructure");
		Elements partElements = partStructureElement.getChildElements();
		ArrayList<StoredModelPart> partList = new ArrayList<StoredModelPart>();
		for(int i = 0; i < partElements.size(); i++)
		{
			Element currentElement = partElements.get(i);
			StoredModelPart modelPart = parseTreeNode(currentElement, model);
			model.addTopLevelPart(modelPart);
		}
		return partList;
	}

	private static StoredModelPart parseTreeNode(Element nodeElement, BlueprintModel model) {
		ModelPartType partType = getPartType(nodeElement);
		String partName = nodeElement.getAttributeValue("nameInModelFile");
		StoredModelPart newPart = new StoredModelPart(partType, partName);
		model.registerPart(newPart);
		parseChildNodes(newPart, nodeElement, model);
		return newPart;
	}

	private static ModelPartType getPartType(Element modelNode) {
		String partName = modelNode.getAttributeValue("name");
		if(partName.equals(PHYSICAL_PART)){
			return ModelPartType.PHYSICAL;
		} else if(partName.equals(SHADER_PART)){
			return ModelPartType.VIRTUAL;
		}
		return ModelPartType.PHYSICAL;
	}
	
	private static void parseChildNodes(StoredModelPart newPart, Element nodeElement, BlueprintModel model) {
		Elements childElements = nodeElement.getChildElements();
		for(int i = 0; i < childElements.size(); i++) {
			newPart.addChild(parseTreeNode(childElements.get(i), model));
		}
	}
}
