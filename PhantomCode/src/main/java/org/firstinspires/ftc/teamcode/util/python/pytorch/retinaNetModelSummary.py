import torchvision
import torchvision.models as models
import torch
from torchvision.models.detection import SSD

model = torchvision.models.detection.ssdlite320_mobilenet_v3_large(pretrained=True)
print(model)