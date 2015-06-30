#version 330

in vec2 position;
in vec2 texCoords;
out vec4 color;

uniform sampler2D image;
uniform mat4 textureMatrix;
uniform vec2 playerPos;

void main()
{
	vec2 texC = (textureMatrix * vec4(texCoords.x, texCoords.y, 0.0f, 1.0f)).xy;
    float radius = 250.0f;
    vec4 origColor = texture(image, texC);
    float gray = dot(origColor.rgb, vec3(0.299, 0.587, 0.114));
    vec2 distVec = position - playerPos;
    float dist = length(distVec);
    float scale = smoothstep(0.0f, radius, dist);
    color = mix(origColor, vec4(gray, gray, gray, origColor.w), scale);
}