#version 400 core

const vec2 lightBias = vec2(0.7, 0.6);//just indicates the balance between diffuse and ambient lighting

in vec2 pass_textureCoordinates;
in vec3 pass_normal;

out vec4 out_Color;

uniform sampler2D diffuseMap;
uniform vec3 lightDirection;

void main(void){

	vec3 unitNormal = normalize(pass_normal);
	float diffuseLight = max(dot(-lightDirection, unitNormal), 0.0) * lightBias.x + lightBias.y;
	out_Color = texture(diffuseMap, pass_textureCoordinates) * diffuseLight;

}