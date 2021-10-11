import torchvision
from torchvision.models.detection.ssdlite import SSDLiteFeatureExtractorMobileNet

model = torchvision.models.detection.ssdlite320_mobilenet_v3_large(pretrained=True)



print(model)