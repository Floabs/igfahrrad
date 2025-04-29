# ig-fahrrad Home Page

## Development

```bash
npm install
npm start
```

Dann [http://127.0.0.1:4200/igfahrrad/public](http://127.0.0.1:4200/igfahrrad/public) öffnen

## Deployment

```bash
cd iffahrrad
./deploy_to_server.bash
```

Then login and rename:
```bash
ssh igfahrrad
tar -xzf www_new.tgz
mv www www.old
mv www_new www
rm -rf www.old
```
