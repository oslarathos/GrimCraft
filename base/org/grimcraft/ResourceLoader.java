package org.grimcraft;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.grimcraft.module.Module;
import org.grimcraft.module.ModuleManager;

public class ResourceLoader {
	private static ResourceLoader instance = new ResourceLoader();
	
	public static ResourceLoader getLoader() {
		return instance;
	}
	
	private URLClassLoader loader;
	private File folder = new File( GrimCraft.getInstance().getDataFolder(), "Resources" );
	private HashMap< String, Class< ? > > classes = new HashMap< String, Class< ? > >();
	
	
	private ResourceLoader() {
		if ( !folder.exists() )
			folder.mkdir();
	}
	
	private void prepareLoader() {
		try {
			loader = new URLClassLoader( getJarLocations(), getClass().getClassLoader() );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	private URL[] getJarLocations() {
		ArrayList< URL > locations = new ArrayList< URL >();
		
		for ( File file : folder.listFiles() ) {
			if ( file.getName().endsWith( ".jar" ) )
				try {
					locations.add( file.toURI().toURL() );
				} catch ( MalformedURLException murle ) {
					System.out.println( "Malformed Resource URL: " + murle.getMessage() );
				}
		}
		
		return locations.toArray( new URL[ locations.size() ] );
	}
	
	public void loadResources() {
		if ( loader != null ) {
			System.out.println( 
				"Due to the limitations of classloading and the nature of this plugin,\n" +
				"ResourceLoader.loadResources may only be called once, if you wish to\n" +
				"reload the assets of this plugin, you must restart the entire server."
			);
			
			return;
		}
		
		prepareLoader();
		
		if ( loader == null )
			return;
		
		for ( File file : folder.listFiles() ) {
			if ( file.getName().endsWith( ".jar" ) && !file.isDirectory() ) {
				try {
					System.out.println( "Opening " + file.getName() );
					
					JarFile jar = new JarFile( file );
					
					Enumeration< JarEntry > entries = jar.entries();
					
					while ( entries.hasMoreElements() ) {
						JarEntry entry = entries.nextElement();
						
						if ( entry.getName().endsWith( ".class" ) ) {
							String path = entry.getName();
							path = path.replaceAll( "/", "." );
							path = path.substring( 0, path.lastIndexOf( ".class" ) );
							
							loadResource( path );
						}
					}
					
				} catch ( Exception e ) {
					e.printStackTrace( System.out );
					
					continue;
				}
			}
		}
	}
	
	private void loadResource( String name ) {
		try {
			Class< ? > clazz = loader.loadClass( name );
			
			if ( clazz == null )
				return;
			
			if ( classes.containsKey( clazz.getName() ) ) {
				System.out.println( "Duplicate class: " + clazz );
			}
			
			classes.put( clazz.getName(), clazz );
			
			if ( Module.class.isAssignableFrom( clazz ) ) {
				Module module = ModuleManager.getInstance().loadModule( clazz.asSubclass( Module.class ) );
				
				if ( module != null ) {
					ModuleManager.getInstance().enableModule( module );
				}
			}
		} catch ( ClassNotFoundException cnfe ) {
			System.out.println( "Class not found: " + name );
		}
	}

	public boolean hasClass( String path ) {
		return classes.containsKey( path );
	}
	
	public Class< ? > getClass( String path ) {
		return classes.get( path );
	}
}