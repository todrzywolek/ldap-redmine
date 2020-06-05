FROM nginx:1.14

COPY nginx.conf /etc/nginx/nginx.conf

ADD build /etc/nginx/html

CMD ["nginx", "-g", "daemon off;"]
