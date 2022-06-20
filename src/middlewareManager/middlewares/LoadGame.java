package middlewareManager.middlewares;

import config.Config;
import middlewareManager.MiddlewareLocation;
import middlewareManager.MiddlewareManager;
import java.io.File;
import java.util.HashMap;
import java.lang.reflect.*;

public class LoadGame extends Middleware {
    int level;
    MiddlewareManager middlewareManager;

    public LoadGame(int level){
        super("LoadGame");
        this.level = level;
        this.middlewareManager = Config.getMiddlewareManager();
    }

    //THIS CLASS DOES NOT CURRENTLY SUPPORT OBJECT ARGUEMENT FOR CONSTRUCTORS!
    //THIS CLASS STILL NEED A PARSER!
    @Override
    public void init(){
        HashMap<String,HashMap<String,String>> data = new HashMap<String, HashMap<String,String>>();
        //Loading from file
        File file = new File("./../../data/levels/"+level+".aa");
        if(file.exists()){
            // parse file
            // get hashmap
        }
        else{
            System.out.println("Level file not found!");
            return;
        }
        
        int i = 0;
        while(true){
        
            if(data.containsKey("middleware"+i)){
                try{
                    //getting class name from hashmap
                    String className = data.get("middleware"+i).get("className");
                    // defining middleware manager arguements
                    Class[] middlewareManagerArgs = new Class[2];
                    middlewareManagerArgs[0] = Middleware.class; middlewareManagerArgs[1]= MiddlewareLocation.class;  
                    // Using Method class to invoke the method from heap on run time.
                    Method addMiddleware = middlewareManager.getClass().getMethod("addMiddleware", middlewareManagerArgs);

                    if(data.get("middleware"+i).containsKey("numberOfArgs")){
                        
                        int numberOfArgs = Integer.parseInt(data.get("middleware"+i).get("numberOfArgs"));

                        Class[] middlwareArgTypes = new Class[numberOfArgs];
                        String[] args = new String[numberOfArgs];
                        for (int j=0 ; j<numberOfArgs ; j++){
                            middlwareArgTypes[j] = String.class;
                            args[j] = data.get("middleware"+i).get("arg"+j);
                        }

                        switch (numberOfArgs){
                            case 0:
                                addMiddleware
                                    .invoke(
                                        middlewareManager,
                                        Class.forName(className).getConstructor().newInstance(),
                                        new MiddlewareLocation());
                                break;
                            case 1:
                                addMiddleware
                                    .invoke(
                                        middlewareManager,
                                        Class.forName(className).getConstructor(middlwareArgTypes).newInstance(args[0]),
                                        new MiddlewareLocation());
                                break;
                            case 2:
                                addMiddleware
                                    .invoke(
                                        middlewareManager,
                                        Class.forName(className).getConstructor(middlwareArgTypes).newInstance(args[0],args[1]),
                                        new MiddlewareLocation());
                                break;
                            case 3:
                                addMiddleware
                                    .invoke(
                                        middlewareManager,
                                        Class.forName(className).getConstructor(middlwareArgTypes).newInstance(args[0],args[1],args[2]),
                                        new MiddlewareLocation());
                                break;
                            case 4:
                                addMiddleware
                                    .invoke(
                                        middlewareManager,
                                        Class.forName(className).getConstructor(middlwareArgTypes).newInstance(args[0],args[1],args[2],args[3]),
                                        new MiddlewareLocation());
                                break;
                            default:
                                //throw error 
                        }
                        
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                    break;
                }
                
            }
            else{
                break;
            }
            i++;
        }
        this.remove();
        
    }

}