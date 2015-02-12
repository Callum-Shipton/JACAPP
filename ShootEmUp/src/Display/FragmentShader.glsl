#version 330

in vec2 texCoords;
out vec4 color;

uniform sampler2D image;
uniform mat4 textureMatrix;

void main()
{
    color = texture(image, (textureMatrix * vec4(texCoords, 0.0f, 0.0f)).xy);
}