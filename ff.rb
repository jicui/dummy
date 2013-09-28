#!/usr/bin/env ruby
class ShapeBranch
	def initialize(url,oldV,newV)
		@url=url
		@oldV=oldV
		@newV=newV
		@poms=[]
	end

	def prtVar
		p "url="+@url+" old version="+@oldV+" new version="+@newV
	end

	def _loadPom(url)
		Dir.foreach(url){|e| 
			if e=="."||e==".."||e=="target" then next end
			_f=url+"\\"+e
			if e=="pom.xml" then @poms<<_f end
			if File.directory? _f then  _loadPom _f end
		}
	end

	def chgver
		prtVar
		a=0
		#load poms
		p "loading pom.xml"
		_loadPom @url
		p @poms.length.to_s+" pom.xml loaded"
		#loop pom and replace the old version
		@poms.each{|pom| 
			#search and replace 
			file=File.read(pom)
			if file.scan(/(#{@oldV})/).length<=0 then next end
			buffer=file.gsub(/(#{@oldV})/,@newV)
			if not File.writable? pom then 
				#check out the file first
				File.open(pom).chmod(0755) 
			end
			f=File.write(pom,buffer)
			a+=1
		}
		p a.to_s+" pom.xml converted"
	end
end

#shapeBranch=ShapeBranch.new("C:\\dev\\stubhub\\domain\\fulfillment\\main","1.9-snapshot","1.pb_fulfillment.0-snapshot")
shapeBranch=ShapeBranch.new("C:\\dev\\stubhub\\domain\\fulfillment\\main","1.pb_fulfillment.0-snapshot","1.9-snapshot")
shapeBranch.chgver